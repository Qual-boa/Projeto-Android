package com.example.qualaboaapp.ui.theme.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.qualaboaapp.R

@Composable
fun BarList(bars: List<BarResponse>, onFavoriteClick: (BarResponse) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(bars) { bar ->
            BarCard(
                image = painterResource(id = R.drawable.profile_image), // Substitua por Coil/Glide para carregar imagens da URL
                title = bar.name,
                description = bar.description,
                additionalInfo = bar.additionalInfo,
                isFavorite = bar.isFavorite,
                onFavoriteClick = { onFavoriteClick(bar) }
            )
        }
    }
}

