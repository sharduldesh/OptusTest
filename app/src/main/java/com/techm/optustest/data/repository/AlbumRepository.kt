package com.techm.optustest.data.repository

import com.techm.optustest.data.network.APIService


class AlbumRepository(private val apiService: APIService) {

    suspend fun getAlbumApi(_id: Int) = apiService.getAlbums(_id)
}