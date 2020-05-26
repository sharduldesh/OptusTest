package com.techm.optustest.data.network

import com.techm.optustest.data.model.AlbumResponseModel
import com.techm.optustest.data.model.UserInfoResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("users")
    suspend fun getUsers(): Response<List<UserInfoResponseModel>>

    @GET("photos")
    suspend fun getAlbums(@Query("albumId") id: Int): Response<List<AlbumResponseModel>>

}