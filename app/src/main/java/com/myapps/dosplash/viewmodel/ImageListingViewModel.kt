package com.myapps.dosplash.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapps.dosplash.api.DestinationService
import com.myapps.dosplash.api.ServiceBuilder
import com.myapps.dosplash.model.Unsplash
import com.myapps.dosplash.utility.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageListingViewModel : ViewModel() {

    var unsplashRandomPhoto: MutableLiveData<Unsplash> = MutableLiveData()

    fun fetchRandomPhoto() {
        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.getRandomPhoto(AppConstants.CLIENT_ID)
        requestCall.enqueue(object : Callback<Unsplash> {
            override fun onResponse(call: Call<Unsplash>, response: Response<Unsplash>) {
                if (response.isSuccessful) {
                    unsplashRandomPhoto?.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Unsplash>, t: Throwable) {
                Log.e("ANKUSH", "${t.message}")
            }
        })
    }
}