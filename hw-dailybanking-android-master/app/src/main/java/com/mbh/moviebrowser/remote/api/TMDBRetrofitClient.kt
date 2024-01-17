package com.mbh.moviebrowser.remote.api

import com.mbh.moviebrowser.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TMDBRetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY))
            .build()
        )
        .build()

    val api: TMDBRetrofitApi = retrofit.create(TMDBRetrofitApi::class.java)
}