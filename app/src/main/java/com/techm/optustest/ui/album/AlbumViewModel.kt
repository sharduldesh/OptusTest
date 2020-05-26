package com.techm.optustest.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techm.optustest.data.model.AlbumResponseModel
import com.techm.optustest.data.repository.AlbumRepository
import com.techm.optustest.util.Constants.Companion.FAILURE_MESSAGE
import kotlinx.coroutines.launch
import retrofit2.Response
import com.techm.optustest.util.Result

/**ViewModel class for album fragment**/
class AlbumViewModel(private val albumRepo: AlbumRepository) : ViewModel() {

    private val albumList = MutableLiveData<Result<Response<List<AlbumResponseModel>>>>()

    /**fetch album list**/
    fun getAlbumList(id: Int): LiveData<Result<Response<List<AlbumResponseModel>>>> {
        return getList(id)
    }

    /**fetch album list form server with in background thread with the help of coroutine**/
    private fun getList(id: Int): LiveData<Result<Response<List<AlbumResponseModel>>>> {

        viewModelScope.launch {
            albumList.postValue(Result.loading(null))
            try {
                albumList.postValue(Result.success(albumRepo.getAlbumApi(id)))
            } catch (exception: Exception) {
                albumList.postValue(Result.error(FAILURE_MESSAGE, null))
                exception.message ?: "Error! $FAILURE_MESSAGE"
            }
        }
        return albumList
    }
}