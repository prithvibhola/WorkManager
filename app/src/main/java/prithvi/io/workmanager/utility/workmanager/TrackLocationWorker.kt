package prithvi.io.workmanager.utility.workmanager

import android.content.Context
import android.os.HandlerThread
import androidx.work.Worker
import androidx.work.WorkerParameters
import prithvi.io.workmanager.data.repository.Repository
import prithvi.io.workmanager.di.module.Provider
import prithvi.io.workmanager.utility.RxBus
import javax.inject.Inject

class TrackLocationWorker @Inject constructor(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @Inject lateinit var repository: Repository

    init {
        Provider.appComponent?.inject(this)
    }

    override fun doWork(): Result {
        val handlerThread = HandlerThread("MyHandlerThread")
        handlerThread.start()
        repository.location.getLocation(handlerThread.looper)
        return Result.SUCCESS
    }
}