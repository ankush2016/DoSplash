package com.myapps.dosplash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImageListingViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ImageListingViewModel::class.java!!)) {
            ImageListingViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}