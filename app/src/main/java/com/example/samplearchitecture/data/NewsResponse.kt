package com.example.samplearchitecture.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)