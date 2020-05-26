package com.techm.optustest.data.model

/**Data class for address**/
data class AddressModel(
    val city: String,
    val geo: GeoModel,
    val street: String,
    val suite: String,
    val zipcode: String
)