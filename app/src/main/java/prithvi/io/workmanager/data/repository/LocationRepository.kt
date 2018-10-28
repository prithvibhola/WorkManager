package prithvi.io.workmanager.data.repository

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.location.*
import io.reactivex.Flowable
import prithvi.io.workmanager.data.models.Response
import prithvi.io.workmanager.data.persistence.Database
import prithvi.io.workmanager.data.persistence.Location
import prithvi.io.workmanager.utility.extentions.locationCallback
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
        private val application: Application,
        private val database: Database,
        private val locationRequest: LocationRequest
) {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    fun getLocation() {
        locationCallback = locationCallback(
                locationResult = {
                    val lastLocation = it?.lastLocation
                    if (lastLocation != null) {
                        saveLocation(Location(0, lastLocation.latitude, lastLocation.longitude, System.currentTimeMillis()))
                    } else {
                    }
                }
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun saveLocation(location: Location) = database.locationDao().insert(location)

    fun getSavedLocation(): Flowable<List<Location>> = database.locationDao().selectAll()
}