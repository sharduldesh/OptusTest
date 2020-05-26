package com.techm.optustest.data.network


class APIServiceImpl: APIService {

    override suspend fun getUsers() = APIBuilder.apiService.getUsers()

    override suspend fun getAlbums(id: Int) = APIBuilder.apiService.getAlbums(id)
}