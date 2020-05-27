package com.techm.optustest.data.repository

import com.techm.optustest.data.model.AlbumResponseModel

/**
 * interface for  album repository
 */
interface AlbumRepository {
  suspend fun getAlbumApi(id: Int) : List<AlbumResponseModel>
}