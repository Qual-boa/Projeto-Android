package com.example.qualaboaapp.ui.theme.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhoto
import com.google.gson.Gson

@Composable
fun BarList(
    establishmentPhotos: Map<String, List<EstablishmentPhoto>>?,
    bars: List<BarResponse>,
    navController: NavController,
    favorites: List<String>,
    onFavoriteClick: (BarResponse) -> Unit,
    distances: Map<String, Float>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (bars.isEmpty()) {
            item {
                Text(
                    text = "Nenhum bar encontrado",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val sortedBars = bars.sortedBy { distances[it.id] ?: Float.MAX_VALUE }
            items(sortedBars) { bar ->
                val photos = establishmentPhotos?.get(bar.id) ?: emptyList()
                val isFavorite = favorites.contains(bar.id)

                CardBar(
                    image = photos.firstOrNull()?.imgUrl,
                    title = bar.fantasyName,
                    description = bar.information.description,
                    additionalInfo = createAmenitiesString(
                        bar.information.hasTv,
                        bar.information.hasAccessibility,
                        bar.information.hasWifi,
                        bar.information.hasParking
                    ),
                    distance = distances[bar.id]?.let { String.format("%.2f", it) } ?: "Desconhecido",
                    isFavorite = isFavorite,
                    onFavoriteClick = { onFavoriteClick(bar) },
                    onClick = {
                        val gson = Gson()
                        val json = gson.toJson(photos)
                        navController.navigate("estabelecimento/${bar.id}?photos=$json")
                    }
                )
            }

            // Spacer para evitar que o último item seja coberto
            item {
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}

fun createAmenitiesString(
    hasTV: Boolean,
    hasAccessibility: Boolean,
    hasWifi: Boolean,
    hasParking: Boolean
): String {
    val amenities = mutableListOf<String>()

    if (hasTV) amenities.add("TV")
    if (hasAccessibility) amenities.add("Acessibilidade")
    if (hasWifi) amenities.add("Wi-fi")
    if (hasParking) amenities.add("Estacionamento")

    return amenities.joinToString(" - ") // Concatena as palavras com " - "
}
