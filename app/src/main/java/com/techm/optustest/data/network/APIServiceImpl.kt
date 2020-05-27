package com.techm.optustest.data.network

import com.techm.optustest.data.model.AlbumResponseModel
import com.techm.optustest.data.repository.AlbumRepository
import com.techm.optustest.data.repository.UserInfoRepository

/**class for calling API**/
class APIServiceImpl(private val apiService: APIInterface): UserInfoRepository , AlbumRepository {

    override suspend fun getUsers() = RetrofitBuilder.apiService.getUsers()

     override suspend fun getAlbumApi(id: Int): List<AlbumResponseModel > = apiService.getAlbums(id)
}