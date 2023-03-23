package com.example.ktorapi.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ktorapi.ui.screens.details.composables.DetailAppBar
import com.example.ktorapi.ui.screens.details.composables.Details
import com.example.ktorapi.ui.screens.details.composables.Episodes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)
@Composable
private fun CharacterScreenContent(
    navController: NavController,
    viewModel: CharacterViewModel = viewModel(),
) {

    val details by viewModel.character.collectAsState()
    val palette by viewModel.palette.collectAsState()

    val pagerState: PagerState = rememberPagerState()
    val scrollBehavior: TopAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DetailAppBar(
                details = details,
                pagerState = pagerState,
                scrollBehavior = scrollBehavior
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (details == null)
                CircularProgressIndicator(color = Color.Black)
            else
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .defaultMinSize(minHeight = 600.dp),
                    count = 2,
                    state = pagerState,
                )
                { page ->
                    when (page) {
                        0 -> Details(details = details)
                        else -> Episodes(episodes = details?.episode ?: listOf(), palette = palette)
                    }
                }
        }
    }
}

@Composable
fun CharacterScreen(
    navController: NavController
) {
    CharacterScreenContent(navController = navController)
}


@Preview
@Composable
private fun CharacterScreenPreview() {
    CharacterScreen(navController = NavController(LocalContext.current))
}