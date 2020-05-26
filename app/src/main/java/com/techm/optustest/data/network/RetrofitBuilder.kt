package com.techm.optustest.data.network

import com.techm.optustest.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**retrofit builder object used for calling API**/
object RetrofitBuilder {

    private fun getNetworkInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: APIInterface = getNetworkInstance().create(APIInterface::class.java)
}