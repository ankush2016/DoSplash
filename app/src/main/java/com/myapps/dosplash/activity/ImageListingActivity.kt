package com.myapps.dosplash.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.myapps.dosplash.R
import com.myapps.dosplash.adapter.UnsplashImagesRVAdapter
import com.myapps.dosplash.model.Unsplash
import com.myapps.dosplash.model.UnsplashRVItems
import com.myapps.dosplash.utility.AppConstants
import com.myapps.dosplash.viewmodel.ImageListingViewModel
import com.myapps.dosplash.viewmodel.ImageListingViewModelFactory
import kotlinx.android.synthetic.main.activity_image_listing.*

class ImageListingActivity : AppCompatActivity() {

    private lateinit var viewModel: ImageListingViewModel
    private lateinit var adapter: UnsplashImagesRVAdapter
    private var mUnsplashRVItemsList = ArrayList<UnsplashRVItems>()

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
        setupRVImageList()
        fetchData()
        viewModel.unsplashRandomPhoto.observe(this, {
            adapter.updateHeaderImage(UnsplashRVItems(AppConstants.ITEM_TYPE_HEADER, it))
            /*Glide.with(this)
                .load(it.urls?.regular)
                *//*.listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        randomPhotoShimmer.stopShimmer()
                        randomPhotoShimmer.visibility = View.GONE
                        return true
                    }

                })*//*
                .thumbnail(Glide.with(this).load(it.urls?.thumb))
                .into(ivRandomPhoto)*/
        })

        viewModel.unsplashPhotosList.observe(this, {
            if (::adapter.isInitialized) {
                for (i in it.indices) {
                    mUnsplashRVItemsList.add(UnsplashRVItems(AppConstants.ITEM_TYPE_LIST_ITEM, it[i]))
                }
                adapter.updateData(mUnsplashRVItemsList)
            }
        })
    }

    private fun setupRVImageList() {
        val layoutManager = LinearLayoutManager(this)
        adapter = UnsplashImagesRVAdapter()
        rvImageList.layoutManager = layoutManager
        rvImageList.adapter = adapter
        rvImageList.isNestedScrollingEnabled = false
    }

    private fun fetchData() {
        viewModel.fetchRandomPhoto()
        //randomPhotoShimmer.startShimmer()

        viewModel.fetchPhotos()
    }

}