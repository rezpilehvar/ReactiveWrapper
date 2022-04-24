package ir.ireza.reactivewrapper.rxjava

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ir.irezaa.reactivewrapper.core.ReactiveTechnology

/*
   Creation Time: 4/24/22
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class RxReactiveTechnology<T : Any> : ReactiveTechnology<T>() {
    private val publishEvent = BehaviorSubject.create<T>()
    private val disposableMap = mutableMapOf<Callback<*>, Disposable>()

    override fun publish(value: T) {
        publishEvent.onNext(value)
    }

    fun observe(): Observable<T> {
        return publishEvent.hide()
    }

    override fun observe(callback: Callback<T>) {
        disposableMap[callback] = publishEvent.subscribe {
            callback.onData(it)
        }
    }

    override fun dispose(callback: Callback<T>) {
        disposableMap[callback]?.let {
            if (it.isDisposed.not()) {
                it.dispose()
            }
            disposableMap.remove(callback)
        }
    }

    override fun lastValue(): T? {
        return publishEvent.value
    }
}