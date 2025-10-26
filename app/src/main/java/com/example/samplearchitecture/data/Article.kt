package com.example.samplearchitecture.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Article(
    val title: String,
    val description: String?,
    val url: String?,
    @Json(name = "urlToImage") val imageUrl: String?,
    val content: String?,
    val publishedAt: String
): Parcelable