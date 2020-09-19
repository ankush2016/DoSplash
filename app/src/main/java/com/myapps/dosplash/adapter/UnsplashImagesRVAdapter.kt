package com.myapps.dosplash.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.dosplash.R
import com.myapps.dosplash.model.Unsplash
import com.myapps.dosplash.model.UnsplashRVItems
import com.myapps.dosplash.utility.AppConstants
import kotlinx.android.synthetic.main.item_row_image.view.*
import kotlinx.android.synthetic.main.item_view_header_image.view.*

class UnsplashImagesRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mUnsplashRVItemsList = ArrayList<UnsplashRVItems>()
    private val ITEM_TYPE_HEADER = 0
    private val ITEM_TYPE_LIST_ITEM = 1

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
                .load(unsplash.urls?.regular)
                .thumbnail(Glide.with(itemView.context).load(unsplash.urls?.thumb))
                .into(ivUnsplashRandomImage)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivUnsplashImage: AppCompatImageView = itemView.ivUnsplashImage
        fun updateData(unsplash: Unsplash) {
            Glide.with(itemView.context)
                .load(unsplash.urls?.regular)
                .thumbnail(Glide.with(itemView.context).load(unsplash.urls?.thumb))
                .into(ivUnsplashImage)
        }
    }
}