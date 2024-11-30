package com.example.qualaboaapp.ui.theme.search

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qualaboaapp.R

@Composable
fun BarList(
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
            items(bars) { bar ->
                Log.d("DistanciaAqui", "${bar.distance.toString()} ")
                BarCard(
                    image = painterResource(id = R.drawable.profile_image),
                    title = bar.fantasyName,
                    description = bar.information.description,
                    additionalInfo = "CNPJ: ${bar.cnpj}, Média: R$${bar.averageOrderValue}",
                    distance = distances[bar.id]?.let { String.format("%.2f", it) } ?: "Desconhecido", // Distância em km
                    isFavorite = false,
                    onFavoriteClick = { onFavoriteClick(bar) },
                    onClick = { navController.navigate("estabelecimento/${bar.id}") }
                )
            }
        }
    }
}