package com.techm.optustest.data.model

/**Data class for Album **/
data class AlbumResponseModel (
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)