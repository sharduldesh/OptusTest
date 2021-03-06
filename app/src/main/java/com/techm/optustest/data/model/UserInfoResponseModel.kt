package com.techm.optustest.data.model

/**Data class for Userinfo **/
data class UserInfoResponseModel (
    val address: AddressModel,
    val company: CompanyModel,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)