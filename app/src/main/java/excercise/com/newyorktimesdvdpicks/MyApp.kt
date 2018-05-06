package excercise.com.newyorktimesdvdpicks

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/*
     Application for project. Contains important static functions
 */
class MyApp: Application() {

    companion object {
        // This is needed for creating the database and using Glide
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }

}