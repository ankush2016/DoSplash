package com.myapps.dosplash.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.myapps.dosplash.R
import com.myapps.dosplash.`interface`.LoadMoreListener
import com.myapps.dosplash.adapter.UnsplashImagesRVAdapter
import com.myapps.dosplash.model.UnsplashRVItems
import com.myapps.dosplash.utility.AppConstants
import com.myapps.dosplash.utility.AppUtility
import com.myapps.dosplash.viewmodel.ImageListingViewModel
import com.myapps.dosplash.viewmodel.ImageListingViewModelFactory
import kotlinx.android.synthetic.main.activity_image_listing.*

class ImageListingActivity : AppCompatActivity(), LoadMoreListener {

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
        })

        viewModel.unsplashPhotosList.observe(this, {
            if (::adapter.isInitialized) {
                mUnsplashRVItemsList.clear()
                for (i in it.indices) {
                    mUnsplashRVItemsList.add(UnsplashRVItems(AppConstants.ITEM_TYPE_LIST_ITEM, it[i]))
                }
                adapter.updateData(mUnsplashRVItemsList)
            }
            checkAndShowLoader(viewModel.mIsDataLoading)
        })
        swipeRefreshLayout.setOnRefreshListener {
            resetData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun resetData() {
        viewModel.pageNo = 1
        viewModel.mIsDataLoading = false
        adapter.clearData()
        fetchData()
    }

    private fun setupRVImageList() {
        val layoutManager = LinearLayoutManager(this)
        adapter = UnsplashImagesRVAdapter(this)
        rvImageList.layoutManager = layoutManager
        rvImageList.adapter = adapter
        rvImageList.setHasFixedSize(true)
    }

    private fun fetchData() {
        if (viewModel.mIsDataLoading) return
        viewModel.fetchRandomPhoto()
        viewModel.fetchPhotos()
        checkAndShowLoader(viewModel.mIsDataLoading)
    }

    override fun onLoadMore() {
        if (viewModel.mIsDataLoading) return
        viewModel.fetchPhotos()
        checkAndShowLoader(viewModel.mIsDataLoading)
    }

    private fun checkAndShowLoader(showLoader: Boolean) {
        if (viewModel.pageNo == 1) {
            showLoader(loaderCenter, showLoader)
        } else {
            showLoader(loaderBottom, showLoader)
            if (!showLoader) {
                showLoader(loaderCenter, showLoader)
            }
        }
    }

    private fun showLoader(loader: LottieAnimationView, showLoader: Boolean) {
        if (showLoader) {
            AppUtility.startLoader(loader)
        } else {
            AppUtility.stopLoader(loader)
        }
    }
}