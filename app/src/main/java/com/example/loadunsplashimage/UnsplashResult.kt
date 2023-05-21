package com.example.loadunsplashimage

import com.google.gson.annotations.SerializedName

data class UnsplashResult(
    @SerializedName("results") val result: List<UnsplashImage>
)