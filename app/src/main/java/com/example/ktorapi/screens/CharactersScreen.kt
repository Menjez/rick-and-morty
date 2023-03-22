package com.example.ktorapi.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.ktorapi.data.viewmodel.CharacterViewModel
import com.example.ktorapi.navigation.Routes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalUnitApi::class)
@Composable
fun PagerContent(
    viewModel: CharacterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {
    val characters by viewModel.list.collectAsState()
    var character by remember { mutableStateOf<com.example.ktorapi.data.dtos.Character?>(null) }
    var page by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState()

    val colorStops = arrayOf(
        0.6f to Color.Transparent,
        1f to Color.Black.copy(alpha = 0.7f)
    )

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            count = characters.size,
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) { index ->

            with(index) {
                page = this
                character = characters[this]
            }

            character?.let {
                Box(modifier = Modifier.clickable {
                    navController.navigate(Routes.Details.route.plus("?id=").plus(it.id))
                }) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.image)
                            .crossfade(true)
                            .build(),
                        loading = { CircularProgressIndicator() },
                        modifier = Modifier
                            .fillMaxSize()
                            .border(BorderStroke(1.dp, Color.Black)),
                        contentDescription = "character image",
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(colorStops = colorStops)
                            )
                    ) {
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(vertical = 64.dp, horizontal = 24.dp)
                    ) {
                        Text(
                            text = it.name,
                            fontSize = TextUnit(36f, TextUnitType.Sp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = it.species + " . " + it.gender,
                            fontSize = TextUnit(20f, TextUnitType.Sp),
                            color = Color.White,

                            )
                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(vertical = 48.dp),
                        activeColor = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
}