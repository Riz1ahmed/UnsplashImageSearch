package com.example.loadunsplashimage

import android.content.Context
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppHelper {
    const val API_KEY = "kaZgPQZ9fxQaCdsTA7haq8LJPTD_EtdKkwpdijC3l8E" // Access Key
    const val secretKey = "8dU5FkPDbTHzwTWYug6gG5GfS74Phw0ErSi-PdqjsZQ" // Secret Key

    fun getRetrofit(): Retrofit {

        val apiInterceptor = getApiKeyInterceptor()

        val client = OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun getApiKeyInterceptor(): Interceptor {
        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newUrl = originalRequest.url.newBuilder()
                .addQueryParameter("client_id", API_KEY)
                .build()

            val newRequest = originalRequest
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
        return apiKeyInterceptor
    }
}

fun Context.showToast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()