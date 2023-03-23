package com.example.ktorapi.ui.screens.details.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.ktorapi.domain.models.CharacterDetails

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Details(details: CharacterDetails?) {
    val list = listOf(
        Pair(
            Color(0xFF592E83), Triple("Name", details?.name ?: "Name", Icons.Rounded.Badge)
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

@Preview
@Composable
private fun DetailsPreview() {
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