package com.myapps.dosplash.api

import com.myapps.dosplash.model.Unsplash
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DestinationService {
    @GET("photos/random")
    fun getRandomPhoto(@Query("client_id") cliendId: String, @Query("orientation") orientation: String = "landscape"): Call<Unsplash>
}