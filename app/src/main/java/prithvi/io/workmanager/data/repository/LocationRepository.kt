package prithvi.io.workmanager.data.repository

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.location.*
import prithvi.io.workmanager.utility.extentions.locationCallback
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
        private val application: Application
) {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 30 * 1000
        fastestInterval = 5 * 1000
    }
    private lateinit var locationCallback: LocationCallback

    fun locationSetup() {
        LocationServices.getSettingsClient(application)
                .checkLocationSettings(
                        LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest)
                                .setAlwaysShow(true)
                                .build())
                .addOnSuccessListener {}
                .addOnFailureListener {
                    Timber.e(it, "Gps not enabled")
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
        fusedLocationClient?.requestLocationUpdates(locationRequest,
                locationCallback,
                null)
    }
}