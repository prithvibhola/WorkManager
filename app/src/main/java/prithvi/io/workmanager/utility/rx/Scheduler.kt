package prithvi.io.workmanager.utility.rx

import io.reactivex.Scheduler

interface Scheduler {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}