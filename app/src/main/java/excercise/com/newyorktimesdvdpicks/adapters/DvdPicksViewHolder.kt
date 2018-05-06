package excercise.com.newyorktimesdvdpicks.adapters

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.widget.RecyclerView
import excercise.com.newyorktimesdvdpicks.databinding.ItemDvdPickBinding
import excercise.com.newyorktimesdvdpicks.models.DvdPick

/*
    View Holder For Dvd Picks
    DvdPicksViewHolder requires a binding class that's auto-generated from the xml layout for the Dvd Pick Item
    The ViewHolder will use the properties defined in the xml to bind data from the DvdPick
 */
class DvdPicksViewHolder(private val binding: ItemDvdPickBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(lifecycleOwner: LifecycleOwner, dvdPick: DvdPick) {
        binding.dvd = dvdPick
        binding.setLifecycleOwner(lifecycleOwner)
        binding.executePendingBindings()
    }

}
