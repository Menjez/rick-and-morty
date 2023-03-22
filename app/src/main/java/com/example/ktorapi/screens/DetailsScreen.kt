package com.example.ktorapi.screens


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ktorapi.data.viewmodel.CharacterDetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    viewModel: CharacterDetailsViewModel = viewModel(),
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabTitles = listOf("Details", "Episode")
    val colorStops = arrayOf(
        0.3f to Color.Transparent,
        1f to Color.Black.copy(alpha = 0.4f)
    )

    val details by viewModel.character.collectAsState()
    val palette by viewModel.palette.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val isCollapsed = remember { derivedStateOf { scrollBehavior.state.collapsedFraction > 0.7 } }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (details != null)
                Box {
                    if (isCollapsed.value.not()) {
                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(details?.image)
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
                                    text = details?.name ?: "",
                                    textAlign = TextAlign.Center,
                                    fontSize = TextUnit(24f, TextUnitType.Sp)
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
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
                            titleContentColor = colorScheme.onPrimary,
                            navigationIconContentColor = colorScheme.onPrimary
                        )
                    )

                    val color = if (isCollapsed.value) Color.Black else Color.White
                    TabRow(
                        modifier = Modifier
                            .padding(top = if (isCollapsed.value) 48.dp else 0.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        selectedTabIndex = pagerState.currentPage,
                        backgroundColor = if (isCollapsed.value) MaterialTheme.colors.background else Color.Transparent,
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
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(8.dp)) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
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
                        0 -> Details()
                        else -> Episodes(episodes = details?.episode ?: listOf(), palette = palette)
                    }
                }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Details(viewModel: CharacterDetailsViewModel = viewModel()) {

    val details by viewModel.character.collectAsState()

    val list = listOf(
        Pair(
            Color(0xFF592E83),
            Triple("Name", details?.name ?: "Name", Icons.Rounded.Badge)
        ),
        Pair(
            Color(0xFFE56399),
            Triple("Species", details?.species ?: "Species", Icons.Rounded.Person)
        ),
        Pair(
            Color(0xFF6DC0D5),
            Triple("Gender", details?.gender ?: "Gender", Icons.Rounded.Transgender)
        ),
        Pair(
            Color(0xFFFFAD05),
            Triple("Origin", details?.origin?.name ?: "Origin", Icons.Rounded.Explore)
        ),
        Pair(
            Color(0xFF48392A),
            Triple("Location", details?.location?.name ?: "Location", Icons.Rounded.Navigation)
        ),
        Pair(
            Color(0xFFA1E887),
            Triple("Status", details?.status ?: "Status", Icons.Rounded.Emergency)
        ),
    )

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        items(items = list) { (color, item) ->
            val (title, value, icon) = item
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.padding(8.dp),
                    backgroundColor = color,
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "name",
                        modifier = Modifier.padding(8.dp),
                        tint = Color.White
                    )
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = title,
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.alpha(0.3f)
                    )
                    Text(
                        text = value,
                        fontSize = TextUnit(24f, TextUnitType.Sp),
                    )
                }
            }
        }
    }
}

private fun Palette.Swatch.rgbAsColor(): Color = Color(rgb)

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

fun Color.isDark() = this.luminance() < 0.5

@Preview
@Composable
fun DetailsPreview() {
    DetailsScreen(navController = NavController(LocalContext.current))
}