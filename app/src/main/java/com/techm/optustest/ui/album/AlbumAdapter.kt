package com.techm.optustest.ui.album

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.techm.optustest.R
import com.techm.optustest.data.model.AlbumResponseModel

/**adapter class for showing item in album fragment**/
class AlbumAdapter(
    private var albumList: List<AlbumResponseModel>,
    private var listener: OnImageClickListener,
    private var context: Context
) : RecyclerView.Adapter<AlbumViewHolder>() {

    /**image click interface**/
    interface OnImageClickListener {
        fun onItemClick(item: AlbumResponseModel?)
    }

    /**holder method for holding item view instance**/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_album,
                parent,
                false
            )
        )

    /**get count of items in list**/
    override fun getItemCount() = albumList.size

    /** bind item view**/
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.itemAlbumBinding.album = albumList[position]
        holder.itemAlbumBinding.imageViewAlbumPhoto.setOnClickListener {
            listener.onItemClick(albumList[position])
        }
    }
}