package com.example.samplearchitecture.data

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class ArticleType : NavType<Article>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): Article? {
        // For modern versions, you might need to use getParcelable(key, Article::class.java)
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key)
        } else {
            bundle.getParcelable(key, Article::class.java)
        }
    }

    override fun parseValue(value: String): Article {
        return Gson().fromJson(value, Article::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Article) {
        bundle.putParcelable(key, value)
    }
}