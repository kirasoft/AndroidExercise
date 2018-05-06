package excercise.com.newyorktimesdvdpicks.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import excercise.com.newyorktimesdvdpicks.R
import excercise.com.newyorktimesdvdpicks.adapters.DvdPicksListAdapter
import excercise.com.newyorktimesdvdpicks.utils.getViewModel
import excercise.com.newyorktimesdvdpicks.viewmodels.DvdPickListViewModel
import kotlinx.android.synthetic.main.fragment_dvd_picks.*

/*
    This Fragment Contains The List of New York Time's Dvd Picks
 */
class DvdPicksListFragment: Fragment() {

    companion object {

        const val TAG = "dvd_pick_list_fragment_key"

        fun newInstance(): DvdPicksListFragment {
            val frag = DvdPicksListFragment()
            frag.retainInstance = true
            return frag
        }

    }

    private var viewModel: DvdPickListViewModel? = null
    private var adapter: DvdPicksListAdapter? = null
    private var viewSaved: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(DvdPickListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewSaved == null) {
            viewSaved = inflater.inflate(R.layout.fragment_dvd_picks, container, false)
        }
        return viewSaved
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpRecyclerView()
        setUpRefresh()
        observeContent()
    }

    private fun setUpRefresh() {
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel?.updateDvdPicksList()
        }
    }

    private fun setUpRecyclerView() {
        dvdPicksRecyclerView?.setHasFixedSize(true)
        dvdPicksRecyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = DvdPicksListAdapter(this, emptyList())
        dvdPicksRecyclerView?.adapter = adapter
    }

    //Observe content from the DvdListViewModel
    private fun observeContent() {
        viewModel?.getDvdPicksList()?.observe(this, Observer { list ->
            if (list?.isNotEmpty() == true) {
                adapter?.updateDataSet(list)
                adapter?.notifyDataSetChanged()
            }
            swipeRefreshLayout?.isRefreshing = false
        })

        viewModel?.errorCallbackAction?.observe(this, Observer {
            Log.e("Error", getString(R.string.error_network), it)
            Toast.makeText(activity, getString(R.string.error_network), Toast.LENGTH_LONG).show()
            swipeRefreshLayout?.isRefreshing = false
        })
    }

}