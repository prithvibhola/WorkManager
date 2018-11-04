package prithvi.io.workmanager.data.repository

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import com.google.android.gms.location.LocationServices
import io.reactivex.Flowable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import prithvi.io.workmanager.data.persistence.Database
import prithvi.io.workmanager.data.persistence.Location
import prithvi.io.workmanager.utility.extentions.checkLocationPermission
import prithvi.io.workmanager.utility.extentions.isGPSEnabled
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
        private val application: Application,
        private val database: Database
) {

    @SuppressLint("MissingPermission")
    fun getLocation() {
        /*
         * One time location request
         */
        if (application.isGPSEnabled() && application.checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(application)
                    ?.lastLocation
                    ?.addOnSuccessListener { location: android.location.Location? ->
                        if (location != null)
                            saveLocation(Location(0, location.latitude, location.longitude, System.currentTimeMillis()))
                    }
        }
    }

    private fun saveLocation(location: Location) = GlobalScope.launch { database.locationDao().insert(location) }

    fun getSavedLocation(): Flowable<List<Location>> = database.locationDao().selectAll()
}