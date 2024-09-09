package com.example.telacadastro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat.startActivity

class LoginActivity : ComponentActivity() {
    private val poppinsFamily = FontFamily(
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_black, FontWeight.Black),
        Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
        Font(R.font.poppins_blackitalic, FontWeight.Black, FontStyle.Italic)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            telaLogin()
        }
    }
}

@Composable
fun telaLogin() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE5B4)) // Cor de fundo
    ) {
        Image(
            painter = painterResource(id = R.mipmap.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().size(300.dp)
        )

        // Centralizar todo o conteúdo
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Ícone de usuário
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 120.dp) // Movendo o ícone mais para baixo
                    .size(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.icon),
                    contentDescription = "User Icon",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Card único que engloba todos os elementos
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, // Centraliza o conteúdo verticalmente
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFFFFF3E0))
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        // Texto "Login" dentro do card
                        Text(
                            text = "LOGIN",
                            color = Color.Black,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = poppinsFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Campos de entrada
                        InputFieldWithShadow(label = "E-mail:", text = "")
                        Spacer(modifier = Modifier.height(16.dp))

                        InputFieldWithShadow(label = "Senha:", text = "", isPassword = true)
                        Spacer(modifier = Modifier.height(24.dp))

                        // Botões estilizados
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Button(
                                onClick = {

                                    // Criar Intent para CadastroActivity
                                    val intentCad = Intent(context, CadastroActivity::class.java)

                                    // Iniciar a atividade CadastroActivity
                                    context.startActivity(intentCad)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(50.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFC107)
                                )
                            ) {
                                Text(
                                    text = "Cadastrar",
                                    color = Color.Black,
                                    fontFamily = FontFamily.Default,
                                    fontSize = 15.2.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }


                            Button(
                                onClick = {
                                    // Ação para "Login"
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(50.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFBD5A0D)
                                )
                            ) {
                                Text(
                                    text = "Login",
                                    color = Color.Black,
                                    fontFamily = FontFamily.Default,
                                    fontSize = 15.2.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputFieldWithShadow(label: String, text: String, isPassword: Boolean = false) {
    val inputValue = remember { mutableStateOf(text) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Default
            ),
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {
            // Sombra da caixa de texto
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = 4.dp, y = 4.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFFBD5A0D)) // Cor da sombra
            )

            // Caixa de entrada branca
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                // Campo de entrada de texto
                BasicTextField(
                    value = inputValue.value,
                    onValueChange = { inputValue.value = it },
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = FontFamily.Default),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    telaLogin()
}
