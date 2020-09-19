package com.myapps.dosplash.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Unsplash(
    @SerializedName("urls") val urls: URLs?,
    @SerializedName("alt_description") val altDescription: String?,
    @SerializedName("user") val user: User?
) : Serializable

data class URLs(
    @SerializedName("raw") val raw: String?,
    @SerializedName("full") val full: String?,
    @SerializedName("regular") val regular: String?,
    @SerializedName("small") val small: String?,
    @SerializedName("thumb") val thumb: String?,
) : Serializable

data class User(
    @SerializedName("profile_image") val profileImage: ProfileImage?,
    @SerializedName("location") val location: String?,
    @SerializedName("name") val name: String?
) : Serializable

data class ProfileImage(
    @SerializedName("medium") val medium: String?
) : Serializable