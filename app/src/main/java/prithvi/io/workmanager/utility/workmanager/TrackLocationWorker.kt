package prithvi.io.workmanager.utility.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import prithvi.io.workmanager.data.repository.Repository
import javax.inject.Inject

class TrackLocationWorker @Inject constructor(
        context: Context,
        params: WorkerParameters,
        val repository: Repository
) : Worker() {

    override fun doWork(): Result {
        repository.location.getLocation()
        return Result.SUCCESS
    }
}