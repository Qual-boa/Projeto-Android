package com.example.qualaboaapp.ui.theme.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.qualaboaapp.R

@Composable
fun CardBar(
    image: String?,
    title: String?,
    description: String,
    additionalInfo: String,
    distance: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp) // Reduzi o padding externo
            .shadow(6.dp, shape = RoundedCornerShape(12.dp))
            .background(Color(0xFFA1530A), shape = RoundedCornerShape(12.dp)) // Menor borda laranja
            .padding(bottom = 3.dp) // Ajuste no espaço inferior
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp) // Altura fixa para garantir alinhamento
                .padding(6.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Imagem do Card
                Image(
                    painter = rememberAsyncImagePainter(image ?: R.drawable.profile_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp) // Imagem menor
                        .clip(CircleShape), // Imagem redonda
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Título
                    if (title != null) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium, // Fonte menor
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Descrição
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall, // Fonte ajustada para menor
                        color = Color.Gray,
                        maxLines = 2, // Limitar a 2 linhas
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Informações adicionais
                    Text(
                        text = additionalInfo,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray,
                        maxLines = 1, // Limitar a 1 linha
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Distância
                    Text(
                        text = "Distância: $distance km",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Ícone de favorito
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}