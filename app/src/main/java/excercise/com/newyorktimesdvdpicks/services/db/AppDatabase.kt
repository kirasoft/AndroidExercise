package excercise.com.newyorktimesdvdpicks.services.db

import android.arch.persistence.room.*
import excercise.com.newyorktimesdvdpicks.models.DvdPick
import io.reactivex.Flowable

/*
    ROOM Database For Offline Use
 */
@Database(entities = [(DvdPick::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dvdPickDao(): DvdPickDao
}

/*
    Data Access Object for Dvd Pick.
    Easily Interface With ROOM Database.
 */
@Dao
interface DvdPickDao {

    @Query("SELECT * FROM dvd_picks")
    fun getDvdPickList(): Flowable<List<DvdPick>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dvdPickList: List<DvdPick>)

    @Query("DELETE FROM dvd_picks")
    fun deleteAll()

}