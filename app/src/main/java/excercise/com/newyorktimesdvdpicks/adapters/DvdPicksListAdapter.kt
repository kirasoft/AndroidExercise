package excercise.com.newyorktimesdvdpicks.adapters

import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import excercise.com.newyorktimesdvdpicks.R
import excercise.com.newyorktimesdvdpicks.databinding.ItemDvdPickBinding
import excercise.com.newyorktimesdvdpicks.models.DvdPick

/*
    RecyclerView Adapter for List of Dvd Picks
 */
class DvdPicksListAdapter(private val lifecycleOwner: LifecycleOwner,
                          private var dvdList: List<DvdPick>): RecyclerView.Adapter<DvdPicksViewHolder>() {

    override fun onBindViewHolder(holder: DvdPicksViewHolder, position: Int) {
        holder.bind(lifecycleOwner, dvdList[position])
    }

    override fun getItemCount(): Int {
        return dvdList.size
    }

    //Bind item_dvd_pick to ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DvdPicksViewHolder {

        val entryBinding = DataBindingUtil.inflate<ItemDvdPickBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_dvd_pick,
                parent,
                false)

        return DvdPicksViewHolder(entryBinding)
    }

    //Replace dvd list with new list
    fun updateDataSet(data: List<DvdPick>){
        dvdList = data
    }

}