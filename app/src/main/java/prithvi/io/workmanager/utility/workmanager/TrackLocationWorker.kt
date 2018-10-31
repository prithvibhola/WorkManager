package prithvi.io.workmanager.utility.workmanager

import android.content.Context
import android.os.HandlerThread
import androidx.work.Worker
import androidx.work.WorkerParameters
import prithvi.io.workmanager.data.repository.Repository
import prithvi.io.workmanager.di.module.Provider
import javax.inject.Inject

class TrackLocationWorker @Inject constructor(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    init {
        Provider.appComponent?.inject(this)
    }

    @Inject lateinit var repository: Repository

    override fun doWork(): Result {

        val handlerThread = HandlerThread("MyHandlerThread");
        handlerThread.start()
        val looper = handlerThread.looper

        repository.location.getLocation(looper)

        return Result.SUCCESS
    }
}