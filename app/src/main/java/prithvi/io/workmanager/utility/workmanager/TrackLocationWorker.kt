package prithvi.io.workmanager.utility.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import javax.inject.Inject

class TrackLocationWorker @Inject constructor(
        context: Context,
        params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}