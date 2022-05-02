package ir.ireza.reactivewrapper.reactor


import ir.irezaa.reactivewrapper.core.ReactiveTechnology
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks


class ReactorReactiveTechnology<T : Any> : ReactiveTechnology<T>() {
    private val publishEvent = Sinks.many().replay().all<T>()
    private val flux = publishEvent.asFlux()
    private val disposableMap = mutableMapOf<Callback<*>, Disposable>()

    override fun observe(callback: Callback<T>) {
        disposableMap[callback] = flux.subscribe {
            callback.onData(it)
        }
    }

    fun flux(): Flux<T> {
        return flux.hide()
    }

    override fun dispose(callback: Callback<T>) {
        disposableMap[callback]?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
            disposableMap.remove(callback)
        }
    }

    override fun publish(value: T) {
        publishEvent.tryEmitNext(value)
    }

    override fun lastValue(): T? {
     return   flux.blockLast()
    }

}