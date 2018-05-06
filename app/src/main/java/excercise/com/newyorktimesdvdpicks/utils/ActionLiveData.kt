package excercise.com.newyorktimesdvdpicks.utils

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread

/*
*   A better way to handle events that should be communicated back to a fragment or activity such as clicks
*   Source: https://android.jlelse.eu/android-arch-handling-clicks-and-single-actions-in-your-view-model-with-livedata-ab93d54bc9dc
 */
class ActionLiveData<T> : MutableLiveData<T>() {

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T?>) {

        super.observe(owner, Observer { data ->

            if (data == null) return@Observer

            observer.onChanged(data)
            // We set the value to null straight after emitting the change to the observer
            value = null
            // This means that the state of the data will always be null / non existent
            // It will only be available to the observer in its callback and since we do not emit null values
            // the observer never receives a null value and any observers resuming do not receive the last event.
            // Therefore it only emits to the observer the single action so you are free to show messages over and over again
            // Or launch an activity/dialog or anything that should only happen once per action / click :).
        })
    }

    @MainThread
    fun sendAction(data: T) {
        value = data
    }

}