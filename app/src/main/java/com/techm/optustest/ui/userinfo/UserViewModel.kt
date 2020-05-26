package com.techm.optustest.ui.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techm.optustest.data.model.UserInfoResponseModel
import com.techm.optustest.data.repository.UserInfoRepository
import com.techm.optustest.util.Constants.Companion.FAILURE_MESSAGE
import kotlinx.coroutines.launch
import com.techm.optustest.util.Result
import retrofit2.Response


/**View model class for user info**/
class UserViewModel(private val userRepo: UserInfoRepository) : ViewModel() {
    private val usersList = MutableLiveData<Result<Response<List<UserInfoResponseModel>>>>()

    init {
        getUsersList()
    }

    /**fetch album list form server with in background thread with the help of coroutine**/
    private fun getUsersList() {
        viewModelScope.launch {
            usersList.postValue(Result.loading(null))
            try {
                usersList.postValue(Result.success(userRepo.getUsersApi()))
            } catch (exception: Exception) {
                usersList.postValue(Result.error(FAILURE_MESSAGE, null))
                exception.message ?: "$FAILURE_MESSAGE"
            }
        }
    }

    /**function returns livedata of userinfo type **/
    fun getUserList(): LiveData<Result<Response<List<UserInfoResponseModel>>>> {
        return usersList
    }
}