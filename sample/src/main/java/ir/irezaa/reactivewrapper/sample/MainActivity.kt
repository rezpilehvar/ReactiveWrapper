package ir.irezaa.reactivewrapper.sample

import android.os.Bundle
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ir.ireza.reactivewrapper.reactor.ReactorReactiveTechnology
import ir.ireza.reactivewrapper.rxjava.RxReactiveTechnology
import ir.irezaa.reactivewrapper.android.ActivityLifeCycleEvent
import ir.irezaa.reactivewrapper.android.ReactiveActivity
import ir.irezaa.reactivewrapper.coroutine.CoroutineReactiveTechnology
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : ReactiveActivity() {
    override val lifecycleReactiveTechnology: CoroutineReactiveTechnology<ActivityLifeCycleEvent> =
        CoroutineReactiveTechnology()

    private val disposables = CompositeDisposable()

    private val coroutineContext: CoroutineContext
        get() = (Dispatchers.Default)

    private val scope = CoroutineScope(coroutineContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        scope.launch {
            lifecycleReactiveTechnology.flow().collect {
                println("NewEvent:CR: $it")
            }
        }
//        disposables.add(lifecycleReactiveTechnology.observe().subscribe {
//            println("NewEvent:RX: $it")
//        })

//        lifecycleReactiveTechnology.flux().subscribe {
//            println("NewEvent:RA: $it")
//        }
    }
}