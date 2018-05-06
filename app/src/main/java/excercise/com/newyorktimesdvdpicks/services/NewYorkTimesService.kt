package excercise.com.newyorktimesdvdpicks.services

import excercise.com.newyorktimesdvdpicks.interfaces.ErrorCallback
import excercise.com.newyorktimesdvdpicks.models.DvdPick
import excercise.com.newyorktimesdvdpicks.services.db.DatabaseManager
import excercise.com.newyorktimesdvdpicks.services.network.NewYorkTimesApi
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/*
    New York Times Network and Room Service.
    Use this class in your ViewModels to access data.
    Data will be taken from API if available, and if not then the Database will be used.
    Make network and db requests on the io thread and observeOn computation
 */
class NewYorkTimesService {

    companion object {
        private const val API_TIME_OUT_MILLISECONDS = 400L
    }

    private val db by lazy { DatabaseManager.database }

    private val api = NewYorkTimesApi()

    //<editor-fold desc="Dvd Pick List">

    private fun getDvdPickListFromDb(): Flowable<List<DvdPick>> = db.dvdPickDao().getDvdPickList()

    //Get DvdPick list from Api, then update ROOM database
    private fun getDvdPickListFromApi(): Flowable<List<DvdPick>> = api.getDvdPickListByDate()
            .doOnNext({data -> saveDvdPickListToDb(data)})

    private fun saveDvdPickListToDb(data: List<DvdPick>) {
        db.dvdPickDao().deleteAll()
        db.dvdPickDao().insertAll(data)
    }

    /*
        The Use of ConcatArrayEager allows us to process an api error
         while still returning data from the cache if available
       Concat will prevent the off chance of database data returning after network data.
       Debounce terminates request after the Timeout time is up
    */
    fun getDvdPickListByDate(errorCallback: ErrorCallback): Flowable<List<DvdPick>> {
        return Flowable.concatArrayEager(
                getDvdPickListFromDb(),
                getDvdPickListFromApi()
                        .materialize()
                        .map {
                            it.error?.let {
                                errorCallback.onError(it)
                            }
                            it
                        }
                        .filter { !it.isOnError }
                        .dematerialize<List<DvdPick>>()
                        .debounce(API_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
    }

    //</editor-fold>

}