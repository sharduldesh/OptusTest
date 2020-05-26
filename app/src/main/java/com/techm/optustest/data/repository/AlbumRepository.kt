package com.techm.optustest.data.repository

import com.techm.optustest.data.network.APIInterface

/**repository class for calling API **/
class AlbumRepository(private val apiService: APIInterface) {

    suspend fun getAlbumApi(_id: Int) = apiService.getAlbums(_id)
}