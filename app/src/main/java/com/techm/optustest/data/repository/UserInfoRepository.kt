package com.techm.optustest.data.repository

import com.techm.optustest.data.network.APIInterface

/**repository class for calling API **/
open class UserInfoRepository(private val apiService: APIInterface) {

    /**get user list from server**/
    suspend fun getUsersApi() = apiService.getUsers()
}