package com.example.ktorapi.ui.screens.details.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette

private fun Palette.Swatch.rgbAsColor(): Color = Color(rgb)
fun Color.isDark() = this.luminance() < 0.5

@Composable
fun Episodes(episodes: List<String>, palette: Palette?) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp), columns = GridCells.Fixed(3)
    ) {
        items(items = episodes) { item ->
            Card(
                modifier = Modifier.padding(8.dp),
                backgroundColor = palette?.dominantSwatch?.rgbAsColor() ?: Color.White,
                elevation = 0.dp,
                border = BorderStroke(1.dp, palette?.dominantSwatch?.rgbAsColor() ?: Color.Black)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = item.replace("https://rickandmortyapi.com/api/episode/", "Episode "),
                    color = if (palette?.dominantSwatch?.rgbAsColor()
                            ?.isDark() == true
                    ) Color.White else Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun EpisodesPreview(){
    Episodes(episodes = listOf(), palette = Palette.from(listOf()))
}
