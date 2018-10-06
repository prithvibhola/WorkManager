package prithvi.io.workmanager.utility.extentions

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import prithvi.io.workmanager.utility.rx.Scheduler

fun <T> Flowable<T>.fromWorkerToMain(scheduler: Scheduler): Flowable<T> =
        this.subscribeOn(scheduler.io()).observeOn(scheduler.mainThread())

fun Disposable.addTo(compositeDisposable: CompositeDisposable) =
        compositeDisposable.add(this)
