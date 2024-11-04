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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R

@Composable
fun ReviewSection() {
    // Lista de avaliações fictícias
    val avaliacoes = listOf(
        Review("Vinicius", "Comida, bebida e muita diversão", 5, R.drawable.profile1),
        Review("Abraão", "Deus abençoe", 5, R.drawable.profile2),
        Review("Maria", "Ambiente agradável", 4, R.drawable.profile1),
        Review("João", "Ótimo lugar para relaxar", 5, R.drawable.profile2)
    )

    // LazyRow para criar a lista horizontal de avaliações
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(avaliacoes) { avaliacao ->
            ReviewCard(
                nome = avaliacao.nome,
                comentario = avaliacao.comentario,
                estrelas = avaliacao.estrelas,
                avatarId = avaliacao.avatarId
            )
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