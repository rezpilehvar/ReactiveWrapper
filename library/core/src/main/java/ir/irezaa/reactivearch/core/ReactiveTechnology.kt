package ir.irezaa.reactivearch.core

abstract class ReactiveTechnology<T> : CallbackReactiveTechnology<T>() {
    abstract fun publish(value : T)
    abstract fun lastValue() : T?
}