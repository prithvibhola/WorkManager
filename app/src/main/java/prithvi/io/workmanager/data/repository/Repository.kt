package prithvi.io.workmanager.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
        val location: LocationRepository
)
