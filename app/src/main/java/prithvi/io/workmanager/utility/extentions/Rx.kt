package prithvi.io.workmanager.utility.extentions

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import prithvi.io.workmanager.utility.rx.Scheduler
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

fun <T> Flowable<T>.fromWorkerToMain(scheduler: Scheduler): Flowable<T> =
        this.subscribeOn(scheduler.io()).observeOn(scheduler.mainThread())

fun Disposable.addTo(compositeDisposable: CompositeDisposable) =
        compositeDisposable.add(this)

fun <T : Any, U: Any> Observable<T>.filterClass(kClass: KClass<U>): Observable<U> = this
        .filter { it::class == kClass }
        .map { kClass.cast(it) }


fun <T : Any, U: Any> Flowable<T>.filterClass(kClass: KClass<U>): Flowable<U> = this
        .filter { it::class == kClass }
        .map { kClass.cast(it) }
