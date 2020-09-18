package com.myapps.dosplash.model

import com.google.gson.annotations.SerializedName

data class Unsplash(
    @SerializedName("urls") val urls: URLs?
)

data class URLs(
    @SerializedName("raw") val raw: String?,
    @SerializedName("full") val full: String?,
    @SerializedName("regular") val regular: String?,
    @SerializedName("small") val small: String?,
    @SerializedName("thumb") val thumb: String?,
)