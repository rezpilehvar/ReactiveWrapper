package ir.irezaa.reactivewrapper.coroutine

import ir.irezaa.reactivewrapper.core.ReactiveTechnology
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*
   Creation Time: 4/24/22
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class CoroutineReactiveTechnology<T> : ReactiveTechnology<T>() {
    private val mutableStateFlow = MutableStateFlow<T?>(null)
    private val internalObservers = mutableMapOf<Callback<*>,Job>()

    private val coroutineContext: CoroutineContext
        get() = (Dispatchers.Default)

    private val scope = CoroutineScope(coroutineContext)

    override fun publish(value: T) {
        mutableStateFlow.tryEmit(value)
    }

    fun flow() : Flow<T?> {
        return mutableStateFlow
    }

    override fun observe(callback: Callback<T>) {
        internalObservers[callback] = scope.launch {
            mutableStateFlow.collect {
                it?.let {
                    callback.onData(it)
                }
            }
        }
    }

    override fun dispose(callback: Callback<T>) {
        internalObservers[callback]?.let {
            if (it.isCancelled.not()) {
                it.cancel()
            }
            internalObservers.remove(callback)
        }
    }

    override fun lastValue(): T? {
        return mutableStateFlow.value
    }
}