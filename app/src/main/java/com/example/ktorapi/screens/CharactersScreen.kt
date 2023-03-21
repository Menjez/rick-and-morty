package com.example.ktorapi.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.google.accompanist.pager.PagerState


/*@Composable
fun CharacterUI(
    viewModel: CharacterDetailsViewModel
){
    val character by viewModel._character.collectAsState()
    Card(modifier = Modifier.background(Color.LightGray)) {
        Column() {
            Text(text = character?.name ?: "")

        }
    }
}*/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalUnitApi::class, ExperimentalMaterialApi::class)
@Composable
fun PagerContent(
    pagerState: PagerState,
    viewModel: CharacterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {

    val character by viewModel.list.collectAsState()

    HorizontalPager(
        count = character.size,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        state = pagerState
    ) { page ->
        val it = character[page]
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            elevation = 1.dp,
            onClick = { navController.navigate(Routes.Details.route.plus("?id=").plus(it.id)) }
        ) {
            Box() {
                Column() {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.image)
                            .crossfade(true)
                            .build(),
                        loading = { CircularProgressIndicator() },
                        modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, Color.Black)),
                        contentDescription = "character image",
                        contentScale = ContentScale.Fit
                    )

                    Column(modifier = Modifier.fillMaxWidth().padding(start = 4.dp, top = 16.dp)) {
                        Text(
                            text = it.name,
                            fontSize = TextUnit(36f, TextUnitType.Sp)
                        )
                        Text(
                            text = "${it.species}" + " . " + "${it.gender}",
                            fontSize = TextUnit(20f, TextUnitType.Sp)
                            )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {

}