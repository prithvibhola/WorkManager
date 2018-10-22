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

}