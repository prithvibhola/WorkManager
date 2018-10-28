package prithvi.io.workmanager.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
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
import prithvi.io.workmanager.utility.workmanager.TrackLocationWorker
import timber.log.Timber
import java.sql.Timestamp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val repository: Repository,
        private val scheduler: Scheduler,
        private val application: Application,
        private val locationRequest: LocationRequest
) : BaseViewModel() {

    val enableLocation: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val location: MutableLiveData<Response<List<Location>>> = MutableLiveData()

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

    fun trackLocation() {
        val locationWorker = OneTimeWorkRequestBuilder<TrackLocationWorker>().build()
        WorkManager.getInstance().enqueue(locationWorker)
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