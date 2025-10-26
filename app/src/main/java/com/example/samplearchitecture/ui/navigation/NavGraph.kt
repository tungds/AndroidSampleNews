package com.example.samplearchitecture.ui.navigation

import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.samplearchitecture.data.Article
import com.example.samplearchitecture.data.ArticleType
import com.example.samplearchitecture.ui.detail.DetailScreen
import com.example.samplearchitecture.ui.home.NewsHomeScreen
import com.example.samplearchitecture.ui.home.NewsHomeViewModel
import com.google.gson.Gson

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    // Navigation graph implementation goes here
    NavHost(navController, startDestination = "home") {
        // Define composable destinations here
        composable("home") {
            NewsHomeScreen(newsHomeViewModel = hiltViewModel<NewsHomeViewModel>()) { article ->
                val json =  Uri.encode(Gson().toJson(article))
                navController.navigate("detail/${json}")
            }
        }


        composable(
            "detail/{article}",
            arguments = listOf(navArgument("article") {
                type = ArticleType()
            })
        ) { backStackEntry ->
            val article = backStackEntry.arguments?.let {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable("article")
                } else {
                    it.getParcelable("article", Article::class.java)
                }
            }

            article?.let {
                DetailScreen(onBackClick = { navController.popBackStack() }, it)
            }
        }
    }
}