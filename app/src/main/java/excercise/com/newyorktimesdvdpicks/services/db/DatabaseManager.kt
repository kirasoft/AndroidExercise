package excercise.com.newyorktimesdvdpicks.services.db

import android.arch.persistence.room.Room
import android.content.Context
import arrow.syntax.function.memoize
import excercise.com.newyorktimesdvdpicks.MyApp
import excercise.com.newyorktimesdvdpicks.utils.Constants.DATABASE_NAME

object DatabaseManager {

    private val createDb: (Context) -> AppDatabase = { context: Context ->
             Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME)
                .build()
    }.memoize()

    val database by lazy { createDb(MyApp.context) }

}