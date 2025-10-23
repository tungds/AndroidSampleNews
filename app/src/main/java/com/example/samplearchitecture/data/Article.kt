package com.example.samplearchitecture.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    val title: String,
    val description: String?,
    val url: String?,
    @Json(name = "urlToImage") val imageUrl: String?,
    val content: String?
)