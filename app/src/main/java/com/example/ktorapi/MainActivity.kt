package com.example.ktorapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.ktorapi.data.viewmodel.CharacterViewModel
import com.example.ktorapi.navigation.Navigation
import com.example.ktorapi.screens.PagerContent
import com.example.ktorapi.ui.theme.KtorAPITheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

class MainActivity : ComponentActivity() {

   // private val viewModel = CharacterDetailsViewModel(savedStateHandle = SavedStateHandle())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                   Navigation()
                }
            }
        }
    }
}

/*@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentView(){
    val pagerState = rememberPagerState()

    PagerContent(pagerState = pagerState, viewModel = CharacterViewModel(), navController = NavController(
        LocalContext.current))

}*/


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KtorAPITheme {

    }
}