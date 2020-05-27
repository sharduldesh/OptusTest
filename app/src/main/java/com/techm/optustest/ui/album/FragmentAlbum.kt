package com.techm.optustest.ui.album

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.techm.optustest.R
import com.techm.optustest.data.model.AlbumResponseModel
import com.techm.optustest.data.network.APIServiceImpl
import com.techm.optustest.data.network.RetrofitBuilder
import com.techm.optustest.data.repository.AlbumRepository
import com.techm.optustest.databinding.FragmentAlbumBinding
import com.techm.optustest.util.Constants.Companion.FAILURE_MESSAGE
import com.techm.optustest.util.Constants.Companion.NO_INTERNET_CONNECTION
import com.techm.optustest.util.Result

import com.techm.optustest.util.isConnection
import com.techm.optustest.util.showSnackBar


/**
 * A simple [Fragment] subclass.
 */
class FragmentAlbum : Fragment(), AlbumAdapter.OnImageClickListener {

    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var albumFactory: AlbumViewModelFactory
    private var userID = 0

    /**get userid from bundle**/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userID = arguments?.getInt("id")!!
    }

    /**inflate fragment view**/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**instantiate fragment on activity create**/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.albumTitle.text = "Album ID: $userID"
        albumFactory = AlbumViewModelFactory(APIServiceImpl(RetrofitBuilder.apiService))
        albumViewModel =
            ViewModelProviders.of(this@FragmentAlbum, albumFactory).get(AlbumViewModel::class.java)

        if (activity?.isConnection()!!) {
            callAlbumApi()
        } else {
            binding.albumRecyclerView.showSnackBar(NO_INTERNET_CONNECTION)
        }

    }

    /**observe user list fetched from server and  bind  recycler view to livedata**/
    private fun callAlbumApi() {

        albumViewModel.getAlbumList(userID).observe(viewLifecycleOwner, Observer {
            it.let { result ->
                when (result.status) {

                    Result.Status.LOADING -> {
                        binding.albumProgressBar.visibility = View.VISIBLE
                        binding.albumRecyclerView.visibility = View.GONE

                    }

                    Result.Status.SUCCESS -> {
                        binding.albumRecyclerView.visibility = View.VISIBLE
                        binding.albumProgressBar.visibility = View.GONE
                        result.data.let { album ->
                            binding.albumRecyclerView.setHasFixedSize(true)
                            binding.albumRecyclerView.adapter = album?.let { albumList ->
                                AlbumAdapter(albumList, this, requireContext())
                            }
                        }
                    }

                    Result.Status.ERROR -> {
                        binding.albumRecyclerView.visibility = View.VISIBLE
                        binding.albumProgressBar.visibility = View.GONE
                        binding.albumRecyclerView.showSnackBar(FAILURE_MESSAGE)
                    }
                }

            }
        })
    }

    /**navigate to album photo fragment**/
    override fun onItemClick(item: AlbumResponseModel?) {

        val bundle = bundleOf(
            "albumID" to item?.albumId,
            "photoID" to item?.id,
            "title" to item?.title,
            "url" to item?.url
        )

        findNavController().navigate(R.id.action_fragmentAlbum_to_fragmentPhoto, bundle)
    }


}
