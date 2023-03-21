package com.example.ktorapi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ktorapi.data.viewmodel.CharacterDetailsViewModel
import com.example.ktorapi.data.viewmodel.CharacterViewModel
import com.example.ktorapi.screens.DetailsScreen
import com.example.ktorapi.screens.PagerContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Character.route
    ) {
        composable(route = Routes.Character.route) {
            val pagerState = rememberPagerState()
            PagerContent(pagerState = pagerState, navController = navController)
        }

        composable(
            route = Routes.Details.route.plus("?id={id}"),
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailsScreen()
        }
    }
}