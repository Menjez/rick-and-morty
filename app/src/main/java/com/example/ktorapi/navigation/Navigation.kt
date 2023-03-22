package com.example.ktorapi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ktorapi.screens.DetailsScreen
import com.example.ktorapi.screens.PagerContent


@Composable
fun Navigation(block: (Boolean) -> Unit) {

    rememberCoroutineScope()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Character.route
    ) {
        composable(route = Routes.Character.route) {
            PagerContent(navController = navController)
        }

        composable(
            route = Routes.Details.route.plus("?id={id}"),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailsScreen(navController = navController)
        }
    }
}