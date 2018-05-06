package excercise.com.newyorktimesdvdpicks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import excercise.com.newyorktimesdvdpicks.fragments.DvdPicksListFragment

/*
    Main Activity
 */
class MainActivity : AppCompatActivity() {

    private val dvdPicksListFragment by lazy {
        supportFragmentManager?.findFragmentByTag(DvdPicksListFragment.TAG) as DvdPicksListFragment?
            ?:  DvdPicksListFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            attachDvdPickListFragment()
        }

    }

    private fun attachDvdPickListFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, dvdPicksListFragment, DvdPicksListFragment.TAG)
                .commit()
    }

}
