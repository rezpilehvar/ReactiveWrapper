package ir.irezaa.reactivewrapper.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.irezaa.reactivewrapper.core.CallbackReactiveTechnology
import ir.irezaa.reactivewrapper.core.ReactiveTechnology

/*
   Creation Time: 4/24/22
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
abstract class ReactiveActivity : AppCompatActivity() {
    abstract val lifecycleReactiveTechnology: ReactiveTechnology<ActivityLifeCycleEvent>

    private val callBack = object : CallbackReactiveTechnology.Callback<ActivityLifeCycleEvent> {
        override fun onData(data: ActivityLifeCycleEvent) {
            println("NewEvent:Callback: $data")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleReactiveTechnology.publish(ActivityLifeCycleEvent.OnCreate)

        lifecycleReactiveTechnology.observe(callBack)
    }

    override fun onStart() {
        super.onStart()
        lifecycleReactiveTechnology.publish(ActivityLifeCycleEvent.OnStart)
    }

    override fun onResume() {
        super.onResume()
        lifecycleReactiveTechnology.publish(ActivityLifeCycleEvent.OnResume)
    }

    override fun onPause() {
        super.onPause()
        lifecycleReactiveTechnology.publish(ActivityLifeCycleEvent.OnPause)
    }

    override fun onStop() {
        super.onStop()
        lifecycleReactiveTechnology.publish(ActivityLifeCycleEvent.OnStop)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleReactiveTechnology.publish(ActivityLifeCycleEvent.OnDestroy)
    }

}

enum class ActivityLifeCycleEvent {
    OnCreate,
    OnStart,
    OnResume,
    OnPause,
    OnStop,
    OnDestroy
}