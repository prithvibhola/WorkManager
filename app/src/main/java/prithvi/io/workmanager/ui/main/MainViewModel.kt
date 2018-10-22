package prithvi.io.workmanager.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import prithvi.io.workmanager.data.models.Response
import prithvi.io.workmanager.data.repository.Repository
import prithvi.io.workmanager.ui.base.BaseViewModel
import prithvi.io.workmanager.utility.extentions.locationCallback
import prithvi.io.workmanager.utility.rx.Scheduler
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val repository: Repository,
        private val scheduler: Scheduler,
        private val application: Application
) : BaseViewModel() {

    val enableLocation: MutableLiveData<Response<Boolean>> = MutableLiveData()

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 30 * 1000
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
        locationCallback = locationCallback(
                locationResult = {
                    val lastLocation = it?.lastLocation
                    if (lastLocation != null) {

                    } else {

                    }
                }
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}