package com.techm.optustest.ui.userinfo

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
import com.techm.optustest.data.model.UserInfoResponseModel
import com.techm.optustest.data.network.APIServiceImpl
import com.techm.optustest.data.network.RetrofitBuilder
import com.techm.optustest.data.repository.UserInfoRepository
import com.techm.optustest.databinding.FragmentUserBinding
import com.techm.optustest.util.Constants.Companion.FAILURE_MESSAGE
import com.techm.optustest.util.Constants.Companion.NO_INTERNET_CONNECTION
import com.techm.optustest.util.Result
import com.techm.optustest.util.isConnection
import com.techm.optustest.util.showSnackBar


/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class FragmentUser : Fragment(), UserAdapter.OnItemClickListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentUserBinding
    private lateinit var userFactory: UserViewModelFactory

    /**bind fragment view**/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**instantiate fragment on activity create**/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val title: String = getString(R.string.user_info)
        binding.mToolbarUserTitle.text = title

        userFactory = UserViewModelFactory(APIServiceImpl(RetrofitBuilder.apiService))
        userViewModel =
            ViewModelProviders.of(this@FragmentUser, userFactory)
                .get(UserViewModel::class.java)

        if (activity?.isConnection()!!) {
            callUserInfoApi()
        } else {
            binding.recyclerViewUser.showSnackBar(NO_INTERNET_CONNECTION)
        }
    }

    /**observe user list fetched from server and  bind  recycler view to livedata**/
    private fun callUserInfoApi() {
        userViewModel.getUserList().observe(viewLifecycleOwner, Observer {
            it.let { result ->
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding.progressBarUser.visibility = View.VISIBLE
                        binding.recyclerViewUser.visibility = View.GONE
                    }
                    Result.Status.SUCCESS -> {
                        binding.recyclerViewUser.visibility = View.VISIBLE
                        binding.progressBarUser.visibility = View.GONE
                        result.data.let { user ->
                            binding.recyclerViewUser.setHasFixedSize(true)
                            binding.recyclerViewUser.adapter =
                                user?.let { userList ->
                                    UserAdapter(
                                        userList,
                                        this,
                                        requireContext()
                                    )

                                }
                        }
                    }
                    Result.Status.ERROR -> {
                        binding.recyclerViewUser.visibility = View.VISIBLE
                        binding.progressBarUser.visibility = View.GONE
                        binding.recyclerViewUser.showSnackBar(FAILURE_MESSAGE)
                    }
                }
            }
        })
    }

    /**navigate to album fragment**/
    override fun onItemClick(item: UserInfoResponseModel?) {
        val bundle = bundleOf("id" to item?.id)
        findNavController().navigate(R.id.action_fragmentUser_to_fragmentAlbum, bundle)
    }
}
