package com.techm.optustest.ui.album

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.techm.optustest.R

/**bind image to the imageview form album recycler view with help of picasso**/
@BindingAdapter("thumbnail")
fun loadAlbumImage(imageView: ImageView , url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.placeholder)
        .into(imageView)

}