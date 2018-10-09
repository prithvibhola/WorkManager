package prithvi.io.workmanager.utility.extentions

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

private val onLocationResultStub = { _: LocationResult? -> Unit }

fun locationCallback(
        locationResult: (locationResult: LocationResult?) -> Unit = onLocationResultStub
): LocationCallback {
    return object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult(locationResult)
        }
    }
}