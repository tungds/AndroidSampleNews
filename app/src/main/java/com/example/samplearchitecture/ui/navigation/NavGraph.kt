package com.example.samplearchitecture.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.samplearchitecture.ui.detail.DetailScreen
import com.example.samplearchitecture.ui.home.NewsHomeScreen
import com.example.samplearchitecture.ui.home.NewsHomeViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    // Navigation graph implementation goes here
    NavHost(navController, startDestination = "home") {
        // Define composable destinations here
        composable("home") { NewsHomeScreen(newsHomeViewModel = hiltViewModel<NewsHomeViewModel>()){ article ->
            navController.navigate("detail/1")
        } }


        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            DetailScreen(navController, id)
        }
    }
}