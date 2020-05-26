package com.techm.optustest.data.repository

import com.techm.optustest.data.network.APIService

open class UserRepository(private val apiService: APIService) {

    suspend fun getUsersApi() = apiService.getUsers()
}