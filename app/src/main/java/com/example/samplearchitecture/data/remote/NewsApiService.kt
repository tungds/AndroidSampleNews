package com.example.samplearchitecture.data.remote

import com.example.samplearchitecture.BuildConfig
import com.example.samplearchitecture.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String = "us",
                                @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY): NewsResponse
}