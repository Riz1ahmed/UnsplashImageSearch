package com.example.loadunsplashimage

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET("/search/photos")
    suspend fun getImages(
        @Query("query") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20,
        @Query("content_filter") contentFilter: String = "high"
    ): Response<UnsplashResult>
}