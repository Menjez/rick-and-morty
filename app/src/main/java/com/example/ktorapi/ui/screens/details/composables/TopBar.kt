package com.example.ktorapi.ui.screens.details.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ktorapi.domain.models.CharacterDetails
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class, ExperimentalPagerApi::class)
@Composable
fun DetailAppBar(
    details: CharacterDetails?, pagerState: PagerState,
    scrollBehavior: TopAppBarScrollBehavior, onBackPressed: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val isCollapsed = remember { derivedStateOf { scrollBehavior.state.collapsedFraction > 0.7 } }

    val tabTitles = listOf("Details", "Episode")
    val colorStops = arrayOf(
        0.3f to Color.Transparent,
        1f to Color.Black.copy(alpha = 0.4f)
    )


    if (details != null)
        Box {
            if (isCollapsed.value.not()) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(details.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier
                            .background(Brush.verticalGradient(colorStops = colorStops))
                            .fillMaxWidth()
                            .height(100.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                    }
                }
            }
            LargeTopAppBar(
                title = {
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 56.dp),
                        visible = isCollapsed.value
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = details.name ?: "",
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(24f, TextUnitType.Sp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "navigate back",
                            tint = if (isCollapsed.value) Color.Black else Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )

            val color = if (isCollapsed.value) Color.Black else Color.White
            TabRow(
                modifier = Modifier
                    .padding(top = if (isCollapsed.value) 48.dp else 0.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = if (isCollapsed.value) androidx.compose.material.MaterialTheme.colors.background else Color.Transparent,
                contentColor = color,
                indicator = {
                    TabRowDefaults.Indicator(
                        color = color,
                        height = 3.dp,
                        modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage])
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, s ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.scrollToPage(index) } },
                        selectedContentColor = color,
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = s,
                            color = color,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    else
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
        }
}

@Composable
@Preview
fun DetailAppBarPreview() {
    Details(
        details = CharacterDetails(
            id = 11224343,
            name = "Menjez",
            gender = "male",
            species = "Martian",
            status = "single as fuck",
            image = "",
            location = CharacterDetails.Location(name = "Maratina", url = ""),
            origin = CharacterDetails.Origin(name = "Kiambu", url = ""),
            episode = listOf()
        )
    )
}