package com.example.qualaboaapp.ui.theme.establishment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
                    avatarId = R.drawable.profile
                )
            }
        }
    }
}

@Composable
fun ReviewCard(nome: String, comentario: String, estrelas: Int, avatarId: Int) {
    Box(
        modifier = Modifier
            .width(250.dp) // Largura fixa para todos os cards
            .height(150.dp) // Altura fixa para manter consistência
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White) // Fundo branco do card
            .border(
                width = 2.dp,
                color = Color(0xFFFFA500), // Cor da borda laranja
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center, // Centralizar elementos verticalmente
            horizontalAlignment = Alignment.CenterHorizontally, // Centralizar elementos horizontalmente
            modifier = Modifier.fillMaxSize() // Preencher todo o espaço disponível
        ) {
            // Avatar
            Image(
                painter = painterResource(id = avatarId),
                contentDescription = "Avatar $nome",
                modifier = Modifier
                    .size(50.dp) // Tamanho fixo do avatar
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Nome
            Text(
                text = nome,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, // Garantir que o nome não extrapole
                textAlign = TextAlign.Center // Centralizar o texto
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Estrelas
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(estrelas) {
                    Icon(
                        painter = painterResource(id = R.drawable.stars_icon),
                        contentDescription = "Star",
                        tint = Color(0xFFFFD700), // Cor das estrelas douradas
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Comentário
            Text(
                text = comentario,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2, // Limitar a 2 linhas
                overflow = TextOverflow.Ellipsis, // Exibir reticências caso o texto seja longo
                textAlign = TextAlign.Center // Centralizar o texto
            )
        }
    }
}