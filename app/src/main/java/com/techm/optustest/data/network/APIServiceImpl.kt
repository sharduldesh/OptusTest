package com.techm.optustest.data.network

/**class for calling API**/
class APIServiceImpl: APIInterface {

    override suspend fun getUsers() = RetrofitBuilder.apiService.getUsers()

    override suspend fun getAlbums(id: Int) = RetrofitBuilder.apiService.getAlbums(id)
}