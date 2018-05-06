package excercise.com.newyorktimesdvdpicks.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import excercise.com.newyorktimesdvdpicks.MyApp

/*
    Container for custom Bindings
 */
class DataBinder {

    companion object {

        //Load Image Asynchronously with Glide
        //Example XML: app:loadImage="http://url.toimage.com"
        @JvmStatic
        @BindingAdapter("loadImage")
        fun ImageView.loadImage(imageUrl: String?) {
            imageUrl?.let { Glide.with(MyApp.context).load(it).into(this) }
        }

    }

}