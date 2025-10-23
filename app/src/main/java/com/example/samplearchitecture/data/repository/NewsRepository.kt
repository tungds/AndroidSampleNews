package com.example.samplearchitecture.data.repository

import com.example.samplearchitecture.data.remote.NewsApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService
){
    suspend fun getArticles() = apiService.getTopHeadlines().articles
}