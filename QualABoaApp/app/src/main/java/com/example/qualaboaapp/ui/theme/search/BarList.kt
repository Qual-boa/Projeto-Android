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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qualaboaapp.R

@Composable
fun BarList(
    bars: List<BarResponse>,
    navController: androidx.navigation.NavController, // Adicionar o NavController
    onFavoriteClick: (BarResponse) -> Unit
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
                android.util.Log.d("BAR_LIST_ITEM", bar.toString()) // Log para debug
                BarCard(
                    image = painterResource(id = R.drawable.profile_image), // Substitua com a lógica correta para carregar imagens
                    title = bar.fantasyName,
                    description = bar.information.description,
                    additionalInfo = "CNPJ: ${bar.cnpj}, Média: R$${bar.averageOrderValue}",
                    isFavorite = false, // Ajuste para lógica de favoritos, se necessário
                    onFavoriteClick = { onFavoriteClick(bar) },
                    onClick = {
                        // Navega para a tela do estabelecimento
                        navController.navigate("estabelecimento/${bar.id}")
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp)) // Espaço para evitar sobreposição pela barra de navegação
        }
    }
}

