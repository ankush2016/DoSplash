package com.myapps.dosplash.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.myapps.dosplash.R
import com.myapps.dosplash.model.Unsplash
import com.myapps.dosplash.utility.AppConstants
import kotlinx.android.synthetic.main.activity_image_details.*

class ImageDetailsActivity : AppCompatActivity() {

    private lateinit var mUnsplash: Unsplash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        init()
        initViews()
    }

    private fun init() {
        mUnsplash = intent.getSerializableExtra(AppConstants.UNSPLASH_ITEM_KEY) as Unsplash
    }

    private fun initViews() {
        Glide.with(this)
            .load(mUnsplash.urls?.full)
            .thumbnail(Glide.with(this).load(mUnsplash.urls?.thumb))
            .into(ivUnsplashImage)

        ivClose.setOnClickListener {
            finish()
        }

        mUnsplash.user?.location?.let {
            tvLocation.text = it
        } ?: run {
            ivLocation.visibility = View.GONE
            tvLocation.visibility = View.GONE
        }

        Glide.with(this)
            .load(mUnsplash.user?.profileImage?.medium)
            .into(ivProfilePic)

        mUnsplash.user?.name?.let {
            tvUserName.text = it
        } ?: run {
            ivProfilePic.visibility = View.GONE
            tvUserName.visibility = View.GONE
        }

        mUnsplash.altDescription?.let {
            imgDescription.text = it
        } ?: run {
            imgDescription.visibility = View.GONE
        }
    }
}