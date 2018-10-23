package prithvi.io.workmanager.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import io.reactivex.rxkotlin.subscribeBy
import prithvi.io.workmanager.data.models.Response
import prithvi.io.workmanager.data.persistence.Location
import prithvi.io.workmanager.data.repository.Repository
import prithvi.io.workmanager.ui.base.BaseViewModel
import prithvi.io.workmanager.utility.extentions.addTo
import prithvi.io.workmanager.utility.extentions.fromWorkerToMain
import prithvi.io.workmanager.utility.extentions.locationCallback
import prithvi.io.workmanager.utility.rx.Scheduler
import timber.log.Timber
import java.sql.Timestamp
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val repository: Repository,
        private val scheduler: Scheduler,
        private val application: Application
) : BaseViewModel() {

    val enableLocation: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val locationStatus: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val location: MutableLiveData<Response<List<Location>>> = MutableLiveData()

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 3 * 1000
        fastestInterval = 5 * 1000
    }
    private lateinit var locationCallback: LocationCallback

    fun locationSetup() {
        enableLocation.value = Response.loading()
        LocationServices.getSettingsClient(application)
                .checkLocationSettings(
                        LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest)
                                .setAlwaysShow(true)
                                .build())
                .addOnSuccessListener { enableLocation.value = Response.success(true) }
                .addOnFailureListener {
                    Timber.e(it, "Gps not enabled")
                    enableLocation.value = Response.error(it)
                }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        locationStatus.value = Response.loading()
        locationCallback = locationCallback(
                locationResult = {
                    val lastLocation = it?.lastLocation
                    if (lastLocation != null) {
                        repository.location.saveLocation(Location(0, lastLocation.latitude, lastLocation.longitude, System.currentTimeMillis()))
                    } else {
                        locationStatus.value = Response.error(Throwable("Could not get your location. Try Again."))
                    }
                }
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun getSavedLocation() {
        repository.location.getSavedLocation()
                .fromWorkerToMain(scheduler)
                .subscribeBy(
                        onNext = {
                            location.value = Response.success(it)
                        },
                        onError = {
                            location.value = Response.error(it)
                        }
                )
                .addTo(getCompositeDisposable())
    }
}