package com.myapps.dosplash.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.myapps.dosplash.R
import com.myapps.dosplash.viewmodel.ImageListingViewModel
import com.myapps.dosplash.viewmodel.ImageListingViewModelFactory
import kotlinx.android.synthetic.main.activity_image_listing.*

class ImageListingActivity : AppCompatActivity() {

    private lateinit var viewModel: ImageListingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_listing)

        init()
        initViews()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, ImageListingViewModelFactory()).get(ImageListingViewModel::class.java)
    }

    private fun initViews() {
        viewModel.fetchRandomPhoto()
        viewModel.unsplashRandomPhoto?.observe(this, {
            Glide.with(this)
                .load(it.urls?.full)
                .thumbnail(Glide.with(this).load(it.urls?.thumb))
                .into(ivRandomPhoto)
        })
    }

}