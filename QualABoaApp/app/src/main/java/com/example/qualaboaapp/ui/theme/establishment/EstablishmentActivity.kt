package com.example.qualaboaapp.ui.theme.establishment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.BottomMenu

class EstablishmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Chamada para a tela principal
            EstablishmentScreen()
        }
    }
}

@Composable
fun EstablishmentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()) // Garantir que o conteúdo seja rolável
    ) {
        // Imagem de Capa
        Image(
            painter = painterResource(id = R.drawable.header_image), // Coloque a imagem de capa no drawable
            contentDescription = stringResource(R.string.imagem_de_capa),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Logo e Informações
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFDBB27)) // Usando a cor de fundo amarela
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.profile_image), // Coloque a logo no drawable
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    // Nome e Avaliação
                    Text(
                        text = "Beer4U",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) {
                            Icon(
                                painter = painterResource(id = R.drawable.stars_icon), // ícone de estrela
                                contentDescription = "Star",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Informações adicionais
                    Text(
                        text = "Estacionamento  -  Acessibilidade  -  TV  -  Wi-fi",
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "FUNCIONAMENTO: seg a dom das 07:00 às 00:00",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "CONTATO: (11) 2345-9898",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Avaliações
        Text(
            text = stringResource(R.string.reviews),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        ReviewSection() // ReviewSection continua horizontal, mas dentro de uma Column

        Spacer(modifier = Modifier.height(16.dp))

        // Localização
        LocationSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Galeria com Carrossel
        Text(
            text = stringResource(R.string.galeria),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        GallerySection()

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Menu
        BottomMenu()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEstablishmentScreen() {
    EstablishmentScreen()
}
