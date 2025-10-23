package com.example.samplearchitecture.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.samplearchitecture.ui.detail.DetailScreen
import com.example.samplearchitecture.ui.home.NewsHomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    // Navigation graph implementation goes here
    NavHost(navController, startDestination = "home") {
        // Define composable destinations here
        composable("home") { NewsHomeScreen(navController) }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            DetailScreen(navController, id)
        }
    }
}