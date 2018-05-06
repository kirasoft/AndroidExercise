package excercise.com.newyorktimesdvdpicks.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import excercise.com.newyorktimesdvdpicks.interfaces.ErrorCallback
import excercise.com.newyorktimesdvdpicks.models.DvdPick
import excercise.com.newyorktimesdvdpicks.services.NewYorkTimesService
import excercise.com.newyorktimesdvdpicks.utils.ActionLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

/*
    DvdPickListFragment ViewModel.
    Prevent data loss when rotating by keeping it in ViewModel
 */
class DvdPickListViewModel: ViewModel(), ErrorCallback {

    private val newYorkTimesService = NewYorkTimesService()
    private val dvdPickList = MutableLiveData<List<DvdPick>>()

    val errorCallbackAction = ActionLiveData<Throwable>()

    fun getDvdPicksList(): LiveData<List<DvdPick>> {
        return dvdPickList
    }

    init {
        updateDvdPicksList()
    }

    fun updateDvdPicksList() {
        newYorkTimesService.getDvdPickListByDate(this as ErrorCallback)
                .subscribe {
                    dvdPickList.postValue(it)
                }
    }

    //<editor-fold desc="ErrorCallback">

    override fun onError(error: Throwable) {
        //Send Error to UI on the mainThread
        Single.fromCallable { errorCallbackAction.sendAction(error) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    //</editor-fold>

}