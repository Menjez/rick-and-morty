package com.example.ktorapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ktorapi.ui.screens.details.CharacterScreen
import com.example.ktorapi.ui.screens.characters.CharactersScreen


@Composable
fun Navigation(block: (Boolean) -> Unit) {

    rememberCoroutineScope()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Characters.route
    ) {
        composable(route = Routes.Characters.route) {
            CharactersScreen(navController = navController)
        }

        composable(
            route = Routes.Character.route.plus("?id={id}"),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            CharacterScreen(navController = navController)
        }
    }
}