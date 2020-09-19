package com.myapps.dosplash.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.dosplash.R
import com.myapps.dosplash.`interface`.LoadMoreListener
import com.myapps.dosplash.activity.ImageDetailsActivity
import com.myapps.dosplash.model.Unsplash
import com.myapps.dosplash.model.UnsplashRVItems
import com.myapps.dosplash.utility.AppConstants
import kotlinx.android.synthetic.main.item_row_image.view.*
import kotlinx.android.synthetic.main.item_view_header_image.view.*

class UnsplashImagesRVAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mUnsplashRVItemsList = ArrayList<UnsplashRVItems>()
    private val ITEM_TYPE_HEADER = 0
    private val ITEM_TYPE_LIST_ITEM = 1
    private lateinit var mLoadMoreListener: LoadMoreListener

    constructor(loadMoreListener: LoadMoreListener) : this() {
        mLoadMoreListener = loadMoreListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_HEADER) {
            HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_header_image, parent, false))
        } else {
            ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_image, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("ANKUSH", "onBindViewHolder")
        if (holder is ItemViewHolder) {
            holder.updateData(mUnsplashRVItemsList[position].unsplash)
        } else if (holder is HeaderViewHolder) {
            holder.updateData(mUnsplashRVItemsList[position].unsplash)
        }

        if (position == mUnsplashRVItemsList.size - 1 && mUnsplashRVItemsList.isNotEmpty() && position != 0) {
            Log.e("ANKUSH", "Load More $position ${mUnsplashRVItemsList.size}")
            mLoadMoreListener.onLoadMore()
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mUnsplashRVItemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mUnsplashRVItemsList[position].itemType == AppConstants.ITEM_TYPE_HEADER) {
            ITEM_TYPE_HEADER
        } else {
            ITEM_TYPE_LIST_ITEM
        }
    }

    fun updateHeaderImage(unsplashItem: UnsplashRVItems) {
        mUnsplashRVItemsList.add(0, unsplashItem)
    }

    fun updateData(unsplashImageList: ArrayList<UnsplashRVItems>) {
        mUnsplashRVItemsList.addAll(unsplashImageList)
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivUnsplashRandomImage: AppCompatImageView = itemView.ivUnsplashRandomImage
        fun updateData(unsplash: Unsplash) {
            Glide.with(itemView.context)
                .load(unsplash.urls?.small)
                .thumbnail(Glide.with(itemView.context).load(unsplash.urls?.thumb))
                .into(ivUnsplashRandomImage)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivUnsplashImage: AppCompatImageView = itemView.ivUnsplashImage
        private val clProfilePic: ConstraintLayout = itemView.clProfilePic
        private val ivProfilePic: AppCompatImageView = itemView.ivProfilePic
        fun updateData(unsplash: Unsplash) {
            Glide.with(itemView.context)
                .load(unsplash.urls?.small)
                .thumbnail(Glide.with(itemView.context).load(unsplash.urls?.thumb))
                .into(ivUnsplashImage)

            Glide.with(itemView.context)
                .load(unsplash.user?.profileImage?.medium)
                .into(ivProfilePic)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ImageDetailsActivity::class.java)
                intent.putExtra(AppConstants.UNSPLASH_ITEM_KEY, unsplash)
                itemView.context.startActivity(intent)
            }
        }
    }
}