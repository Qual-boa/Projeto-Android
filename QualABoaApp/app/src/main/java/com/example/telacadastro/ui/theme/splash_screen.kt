package com.example.telacadastro.ui.theme

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telacadastro.R

class SplashActivity : ComponentActivity() {

    // Definindo a família de fontes Poppins
    private val poppinsFamily = FontFamily(
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_black, FontWeight.Black),
        Font(R.font.poppins_extrabold, FontWeight.ExtraBold)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Exibe a SplashScreen por 3 segundos antes de abrir a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finaliza a SplashActivity para que ela não volte ao pressionar "back"
        }, 3000)

        setContent {
            SplashScreen()
        }
    }

    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFE5B4)) // Cor de fundo
        ) {
            // Imagem de fundo
            Image(
                painter = painterResource(id = R.mipmap.backgroundd), // Coloque o ID correto da sua imagem de fundo aqui
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Conteúdo centralizado
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Texto de boas-vindas
                Text(
                    text = "Bem vindo ao",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFamily,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.mipmap.logo), // Coloque o ID correto da sua imagem de fundo aqui
                    contentDescription = null
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SplashScreenPreview() {
        SplashScreen()
    }
}
