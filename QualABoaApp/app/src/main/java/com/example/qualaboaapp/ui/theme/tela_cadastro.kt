package com.example.qualaboaapp.ui.theme

import LoginActivity
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.qualaboaapp.R

class CadastroActivity : ComponentActivity() {
    private val poppinsFamily = FontFamily(
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_black, FontWeight.Black),
        Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
        Font(R.font.poppins_blackitalic, FontWeight.Black, FontStyle.Italic),
    )

    private val montserratFamily = FontFamily(
        Font(R.font.montserrat, FontWeight.Bold),
        Font(R.font.montserrat, FontWeight.ExtraBold)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationScreen()
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
                painter = painterResource(id = R.mipmap.backgroundd),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().size(300.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 60.dp)
                        .size(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.icon),
                        contentDescription = "User Icon",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(150.dp))

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
                            Text(
                                text = stringResource(R.string.cadastro_titulo),
                                color = Color.Black,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = poppinsFamily,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            InputFieldWithShadow(label = stringResource(R.string.nome_label), text = "")
                            Spacer(modifier = Modifier.height(16.dp))

                            InputFieldWithShadow(label = stringResource(R.string.email_label), text = "")
                            Spacer(modifier = Modifier.height(16.dp))

                            InputFieldWithShadow(label = stringResource(R.string.password_label), text = "", isPassword = true)
                            Spacer(modifier = Modifier.height(16.dp))

                            InputFieldWithShadow(label = stringResource(R.string.confirmar_senha_label), text = "", isPassword = true)

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Button(
                                    onClick = {
                                        val intent = Intent(this@CadastroActivity,
                                            LoginActivity::class.java)
                                        startActivity(intent)                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(50.dp)),
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFFC107)
                                    )
                                ) {
                                    Text(
                                        text = stringResource(R.string.entrar_button),
                                        color = Color.Black,
                                        fontFamily = poppinsFamily,
                                        fontSize = 15.2.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Button(
                                    onClick = {

                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(50.dp)),
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFBD5A0D)
                                    )
                                ) {
                                    Text(
                                        text = stringResource(R.string.cadastrar_button),
                                        color = Color.Black,
                                        fontFamily = poppinsFamily,
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
}