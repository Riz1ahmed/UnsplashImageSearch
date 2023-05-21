package com.example.loadunsplashimage

import com.google.gson.annotations.SerializedName

data class UnsplashImage(
    @SerializedName("id")
    val id: String,

    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("urls")
    val urls: ImageUrls
)

data class ImageUrls(
    @SerializedName("raw")
    val raw: String,
    @SerializedName("full")
    val full: String,
    @SerializedName("regular")
    val regular: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("thumb")
    val thumb: String,
)