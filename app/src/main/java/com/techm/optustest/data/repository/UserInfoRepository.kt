package com.techm.optustest.data.repository

import com.techm.optustest.data.model.UserInfoResponseModel

/**
 * interface for  userinfo repository
 */
interface UserInfoRepository{
    suspend fun getUsers() :List<UserInfoResponseModel>
}