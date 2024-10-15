package com.example.telacadastro.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.telacadastro.R
import com.example.telacadastro.ui.components.NotificationCard  // Importando o NotificationCard

class tela_notificacao : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaCadastroTheme {
                NotificationScreen()  // Chamando a tela de notificação
            }
        }
    }
}

@Composable
fun NotificationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1)) // Cor de fundo similar
    ) {
        // Remover o offset para que a área branca vá até o fundo da tela
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
                // Pequena margem superior ajustada
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Notificações",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                // Exibe o NotificationCard
                NotificationCard(
                    imageRes = R.mipmap.perfil,  // Substitua pelo recurso de imagem correto
                    title = "Bar do Vini",
                    description = "Comida, bebida e muita diversão",
                    services = listOf("Estacionamento", "Acessibilidade", "TV", "Wi-Fi")
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espaçamento após o card

                // Centralizar o texto "Aqui estão suas notificações."
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Aqui estão suas notificações.")
                }
            }
        }

        // Menu inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFFFFF1D5))
                .zIndex(2f)
        ) {
            NotificationBottomMenu()  // Função de menu da notificação
        }
    }
}

@Composable
fun NotificationBottomMenu() {  // Renomeado para "NotificationBottomMenu"
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.home),
                contentDescription = "Home",
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.not),
                contentDescription = "Notificações",
                modifier = Modifier.size(30.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(70.dp)
                .background(Color(0xFFA1530A), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.mipmap.search),
                    contentDescription = "Pesquisa",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }
        }

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.fav),
                contentDescription = "Favoritos",
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.user),
                contentDescription = "Perfil",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationPreview() {
    TelaCadastroTheme {
        NotificationScreen()
    }
}
