package com.techm.optustest.ui.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techm.optustest.data.repository.UserInfoRepository

/**factory method to instantiate viewmodel**/
@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val userRepository: UserInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository) as T
    }
}
