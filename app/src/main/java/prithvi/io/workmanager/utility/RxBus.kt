package prithvi.io.workmanager.utility

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import prithvi.io.workmanager.utility.extentions.filterClass
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KClass

class RxBus<T : Any> {

    private val bus = PublishProcessor.create<T>()
    private val stickyEvents = CopyOnWriteArrayList<T>()
    // private val stickyEvents = Collections.synchronizedList(ArrayList<T>())

    /**
     * @return io.reactivex.Flowable stream of events and sticky events
     */
    fun getsticky(): Flowable<T> {
        return Flowable.merge(
                bus.doOnNext { stickyEvents.remove(it) },
                Flowable.fromIterable(stickyEvents))
    }

    /**
     * @return Flowable stream of events casted to a class Type
     */
    fun <T : Any> get(kClass: KClass<T>): Flowable<T> = bus.filterClass(kClass)

    /**
     * @return Flowable stream of events
     */
    fun get(): Flowable<T> = bus

    /**
     * Sends an event into the event bus
     *
     * @param obj Event item
     */
    fun send(obj: T) = bus.onNext(obj)

    /**
     * Adds an event into the event bus as sticky, which remains unless removed
     *
     * @param obj Event item
     */
    fun sendSticky(obj: T) {
        bus.onNext(obj)
        stickyEvents.add(obj)
    }

    fun removeStickyEvent(obj: T) = stickyEvents.remove(obj)

    fun <U : T> removeSticky(kClass: KClass<U>) {
        stickyEvents
                .filter { it::class == kClass }
                .forEach { stickyEvents.remove(it) }
    }

    /**
     * flush the sticky stream for new subscribers
     */
    fun flush() = stickyEvents.clear()

}