package ir.irezaa.reactivewrapper.core

abstract class CallbackReactiveTechnology<T> {
    abstract fun observe(callback : Callback<T>)
    abstract fun dispose(callback: Callback<T>)

    interface Callback<in T> {
        fun onData(data : T)
    }
}