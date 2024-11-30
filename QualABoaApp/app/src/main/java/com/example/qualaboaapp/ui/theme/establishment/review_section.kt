package com.example.qualaboaapp.ui.theme.establishment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R

@Composable
fun ReviewSection(reviews: List<ReviewWithUser>) {
    if (reviews.isEmpty()) {
        Text(
            text = "Nenhuma avaliação disponível",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(reviews) { review ->
                ReviewCard(
                    nome = review.userName,
                    comentario = review.message,
                    estrelas = review.rate.toInt(),
                    avatarId = R.drawable.profile_image
                )
            }
        }
    }
}

@Composable
fun ReviewCard(nome: String, comentario: String, estrelas: Int, avatarId: Int) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
            .width(200.dp) // Largura fixa para cada card
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            Image(
                painter = painterResource(id = avatarId),
                contentDescription = "Avatar $nome",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = nome, fontWeight = FontWeight.Bold)
                Row {
                    repeat(estrelas) {
                        Icon(
                            painter = painterResource(id = R.drawable.stars_icon),
                            contentDescription = "Star",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = comentario, fontSize = 12.sp)
    }
}