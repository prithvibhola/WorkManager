package prithvi.io.workmanager.data.repository

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import com.google.android.gms.location.*
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.GlobalScope
import prithvi.io.workmanager.data.models.Response
import prithvi.io.workmanager.data.persistence.Database
import prithvi.io.workmanager.data.persistence.Location
import prithvi.io.workmanager.utility.extentions.locationCallback
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.launch

@Singleton
class LocationRepository @Inject constructor(
        private val application: Application,
        private val database: Database,
        private val locationRequest: LocationRequest
) {

//    private var fusedLocationClient: FusedLocationProviderClient? = null
//    private lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    fun getLocation(looper: Looper) {

        /*
         * Fused Location when location updates are required
         */
//        locationCallback = locationCallback(
//                locationResult = {
//                    val lastLocation = it?.lastLocation
//                    if (lastLocation != null) {
//                        saveLocation(Location(0, lastLocation.latitude, lastLocation.longitude, System.currentTimeMillis()))
//                    } else {
//                    }
//                }
//        )
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
//        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, looper)

        /*
         * One time location request
         */
        LocationServices.getFusedLocationProviderClient(application)
                ?.lastLocation
                ?.addOnSuccessListener { location: android.location.Location? ->
                    if (location != null)
                        saveLocation(Location(0, location.latitude, location.longitude, System.currentTimeMillis()))
                }
    }

    private fun saveLocation(location: Location) = GlobalScope.launch { database.locationDao().insert(location) }

    fun getSavedLocation(): Flowable<List<Location>> = database.locationDao().selectAll()
}