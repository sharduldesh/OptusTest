package com.techm.optustest.ui.photo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.techm.optustest.R
import com.techm.optustest.databinding.FragmentAlbumDetailsBinding
import kotlinx.android.synthetic.main.header_view.view.*

/**photo fragment**/
class FragmentPhoto: Fragment() {

    private lateinit var binding: FragmentAlbumDetailsBinding
    private var albumID = 0
    private var photoID = 0
    private var title = ""
    private var url = ""

    /**inflate fragment**/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlbumDetailsBinding.inflate(inflater , container , false)
        return binding.root
    }

    /**assign values fetched from bundle **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumID = arguments?.getInt("albumID")!!
        photoID = arguments?.getInt("photoID")!!
        title = arguments?.getString("title")!!
        url = arguments?.getString("url")!!
    }

    /**bind image and text from layout**/
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolBarHeaderView.textViewAlbumDetailID.text = "Album ID : $albumID"
        binding.toolBarHeaderView.textViewPhotoDetailID.text = "Photo ID : $photoID"
        binding.albumDetailTitle.text = title
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(binding.imageViewAlbumImage)
    }
}