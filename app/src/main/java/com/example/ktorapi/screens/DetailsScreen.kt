package com.example.ktorapi.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ktorapi.data.viewmodel.CharacterDetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(viewModel: CharacterDetailsViewModel = viewModel()) {

    val details by viewModel._character.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Details") })
    }) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Column() {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(details?.image)
                        .crossfade(true).build(),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth()
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    SwipableSample()
                }
            }
        }
    }
}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwipableSample() {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabTitles = listOf("Details", "Episode")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(modifier = Modifier.fillMaxWidth(), selectedTabIndex = pagerState.currentPage) {
            tabTitles.forEachIndexed { index, s ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } }
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = s)
                }
            }
        }
        HorizontalPager(modifier = Modifier.weight(1f), count = 2, state = pagerState) {
            when (it) {
                0 -> details()
                else -> episodes()
            }
        }

    }
}

@Composable
fun details(viewModel: CharacterDetailsViewModel = viewModel()) {
    val details by viewModel._character.collectAsState()
    Column() {
        Text(text = details?.name ?: "")
        Text(text = details?.species ?: "")
        Text(text = details?.gender ?: "")
    }
}

@Composable
fun episodes(viewModel: CharacterDetailsViewModel = viewModel()) {
    val details by viewModel._character.collectAsState()
    Column() {
        val episode = details?.episode
        episode?.forEach { it ->
            Text(text = episode.toString())
        }
    }
}

@Preview
@Composable
fun detailsPreview() {
    DetailsScreen()
}