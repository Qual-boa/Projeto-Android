package com.example.telacadastro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definindo a família de fontes Poppins
val poppinsFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_blackitalic, FontWeight.Black, FontStyle.Italic),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QualABoaScreen(
                onCadastroClick = {
                    val intent = Intent(this, CadastroActivity::class.java)
                    startActivity(intent)
                },
                onLoginClick = {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun QualABoaScreen(
    onCadastroClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagem de fundo
        Image(
            painter = painterResource(id = R.mipmap.backgroundd), // Coloque o ID correto da sua imagem de fundo aqui
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().size(300.dp)
        )

        // Conteúdo da tela
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.mipmap.logo), // Coloque o ID correto da sua imagem de fundo aqui
                contentDescription = null
            )
            // Botões de Cadastro e Login
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botão Cadastro
                ButtonWithIcon(
                    text = "CADASTRO",
                    iconRes = R.mipmap.botao1, // Substitua pelo ícone correto
                    onClick = onCadastroClick
                )

                // Botão Login
                ButtonWithIcon(
                    text = "LOGIN",
                    iconRes = R.mipmap.boato2, // Substitua pelo ícone correto
                    onClick = onLoginClick
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Texto "Entrar sem logar" sublinhado
            Text(
                text = "Entrar sem logar",
                color = Color.Black,
                fontFamily = poppinsFamily,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun ButtonWithIcon(text: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .width(180.dp) // Definindo a largura fixa para que os botões tenham o mesmo tamanho
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Texto do botão, alinhado à esquerda
            Text(
                text = text,
                color = Color.Black,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f) // Garante que o texto ocupe o espaço disponível
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Círculo com ícone, alinhado à direita
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFBD5A0D)) // Cor laranja escura para o círculo
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QualABoaScreenPreview() {
    QualABoaScreen({}, {})
}
