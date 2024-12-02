package com.example.qualaboaapp.ui.theme.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    onFavoriteClick: (BarResponse) -> Unit,
    distances: Map<String, Float> // Adiciona as distâncias calculadas
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
            val sortedBars = bars.sortedByDescending { bar ->
                distances[bar.id] // Ordena usando o valor de distância associado ao ID do bar
            }
            items(sortedBars) { bar ->
                val photos = establishmentPhotos?.get(bar.id) ?: emptyList()

                var additionalInfo = createAmenitiesString(
                    bar.information.hasTv,
                    bar.information.hasAccessibility,
                    bar.information.hasWifi,
                    bar.information.hasParking
                )
                CardBar(
                    image = photos.first().imgUrl,
                    title = bar.fantasyName,
                    description = bar.information.description,
                    additionalInfo = additionalInfo,
                    distance = distances[bar.id]?.let { String.format("%.2f", it) } ?: "Desconhecido", // Distância em km
                    isFavorite = false,
                    onFavoriteClick = { onFavoriteClick(bar) },
                    onClick = {
                        val gson = Gson()
                        val json = gson.toJson(photos)
                        navController.navigate("estabelecimento/${bar.id}?photos=$json")
                    }
                )
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
