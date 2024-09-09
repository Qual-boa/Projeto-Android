package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telacadastro.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
val montserratFamily = FontFamily(
    Font(R.font.montserrat, FontWeight.Bold),
    Font(R.font.montserrat, FontWeight.ExtraBold)
)

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
            RegistrationScreen()
        }
    }
}

@Composable
fun RegistrationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE5B4))
    ) {
        Image(
            painter = painterResource(id = R.mipmap.back_tela),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.icon),
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "CADASTRO",
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = poppinsFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(50.dp))

            InputFieldWithShadow(label = "Nome:", text = "")
            Spacer(modifier = Modifier.height(16.dp))

            InputFieldWithShadow(label = "E-mail:", text = "")
            Spacer(modifier = Modifier.height(16.dp))

            InputFieldWithShadow(label = "Senha:", text = "", isPassword = true)
            Spacer(modifier = Modifier.height(16.dp))

            InputFieldWithShadow(label = "Confirmar Senha:", text = "", isPassword = true)

            Spacer(modifier = Modifier.height(50.dp))

            // Botões
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFDBB27)
                    )
                ) {
                    Text(text = "ENTRAR", color = Color.Black, fontFamily = poppinsFamily,
                        fontSize = 18.sp, fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBD5A0D)
                    )
                ) {
                    Text(text = "CADASTRAR", color = Color.Black, fontFamily = poppinsFamily,
                        fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontFamily = poppinsFamily
            ),
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = 6.dp, y = 6.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFFBD5A0D))
            )

            // Caixa branca acima da sombra
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
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = poppinsFamily),
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
fun RegistrationScreenPreview() {
    RegistrationScreen()
}
