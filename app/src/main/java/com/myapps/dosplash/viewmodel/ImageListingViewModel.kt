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

    val unsplashRandomPhoto: MutableLiveData<Unsplash> = MutableLiveData()
    val unsplashPhotosList: MutableLiveData<List<Unsplash>> = MutableLiveData()
    var pageNo: Int = 1
    var mIsDataLoading = false

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

    fun fetchPhotos() {
        mIsDataLoading = true
        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.getPhotos(AppConstants.CLIENT_ID, pageNo)
        requestCall.enqueue(object : Callback<List<Unsplash>> {
            override fun onResponse(call: Call<List<Unsplash>>, response: Response<List<Unsplash>>) {
                mIsDataLoading = false
                if (response.isSuccessful) {
                    pageNo++
                    Log.e("ANKUSH", "pageNo = $pageNo")
                    unsplashPhotosList?.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<Unsplash>>, t: Throwable) {
                mIsDataLoading = false
            }
        })
    }
}