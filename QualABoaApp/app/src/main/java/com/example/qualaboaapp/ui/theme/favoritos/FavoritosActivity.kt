package com.example.qualaboaapp.ui.theme.favoritos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.BottomMenu // Importa o BottomMenu reutilizável
import com.example.qualaboaapp.ui.theme.utils.NotificationCard

class FavoritosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavoriteScreen()
        }
    }
}

@Composable
fun FavoriteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .background(Color.White)
                .zIndex(1f)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start, // Alinha à esquerda
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.favoritos),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                // Adicionando o NotificationCard
                repeat(3) {
                    NotificationCard(
                        imageRes = R.mipmap.perfil,
                        title = "Bar do Vini",
                        description = "Comida, bebida e muita diversão",
                        services = listOf("Estacionamento", "Acessibilidade", "TV", "Wi-Fi"),
                        isFavorite = true,
                        onFavoriteClick = {
                            println("Favorito clicado!")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Espaçamento entre os cards
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.favorites_loaded_message))
                }
            }
        }

        // Reutiliza o BottomMenu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFFFFF1D5))
                .zIndex(2f)
        ) {
            BottomMenu() // Chamando o BottomMenu reutilizável
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FavoriteScreen()
}
