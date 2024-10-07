package com.example.telacadastro.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.telacadastro.R

val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        Image(
            painter = painterResource(id = R.mipmap.circulo),
            contentDescription = "Círculo Laranja",
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopCenter)
                .offset(y = 20.dp)
                .zIndex(0f)
        )

        Image(
            painter = painterResource(id = R.mipmap.icone),
            contentDescription = "User Icon",
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.TopCenter)
                .offset(y = 70.dp)
                .zIndex(2f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .align(Alignment.TopCenter)
                .offset(y = 140.dp)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(Color.White)
                .zIndex(1f)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = "Perfil",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                ProfileTextFieldWithShadow(text = "Matheus")
                Spacer(modifier = Modifier.height(16.dp))
                ProfileTextFieldWithShadow(text = "matheus@email.com")
                Spacer(modifier = Modifier.height(16.dp))
                ProfileTextFieldWithShadow(text = "*******", isPassword = true)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA1530A))
                ) {
                    Text(
                        text = "Salvar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFFFFF1D5))
                .zIndex(2f)
        ) {
            BottomMenu()
        }
    }
}




@Composable
fun ProfileTextFieldWithShadow(text: String, isPassword: Boolean = false, onValueChange: (String) -> Unit = {}) {
    val inputValue = remember { mutableStateOf(text) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 4.dp, y = 4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFFBD5A0D))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = inputValue.value,
                    onValueChange = {
                        inputValue.value = it
                        onValueChange(it)
                    },
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = PoppinsFontFamily
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                        .weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.mipmap.edit),
                    contentDescription = "Edit Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Composable
fun BottomMenu() {
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

        IconButton(onClick = {}) {
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
fun DefaultPreview() {
    ProfileScreen()
}
