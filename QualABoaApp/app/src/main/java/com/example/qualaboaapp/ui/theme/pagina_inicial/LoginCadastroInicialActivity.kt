package com.example.qualaboaapp.ui.theme.pagina_inicial

import com.example.qualaboaapp.ui.theme.login.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.MainActivity
import com.example.qualaboaapp.ui.theme.cadastro.CadastroActivity

val poppinsFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_blackitalic, FontWeight.Black, FontStyle.Italic),
)

class LoginCadastroInicialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QualABoaScreen(
                onCadastroClick = {
                    val intent = Intent(this@LoginCadastroInicialActivity, CadastroActivity::class.java)
                    startActivity(intent)
                },
                onLoginClick = {
                    val intent = Intent(this@LoginCadastroInicialActivity, LoginActivity::class.java)
                    startActivity(intent)
                },
                onEntrarSemLogarClick = {
                    val intent = Intent(this@LoginCadastroInicialActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun QualABoaScreen(
    onCadastroClick: () -> Unit,
    onLoginClick: () -> Unit,
    onEntrarSemLogarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.mipmap.backgroundd),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .size(300.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonWithIcon(
                    text = stringResource(R.string.cadastro_button),
                    iconRes = R.mipmap.botao1,
                    onClick = {
                        Log.d("ButtonClick", "Cadastro button clicked")
                        onCadastroClick()
                    }
                )

                ButtonWithIcon(
                    text = stringResource(R.string.login_button),
                    iconRes = R.mipmap.boato2,
                    onClick = onLoginClick
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.entrar_sem_logar),
                color = Color.Black,
                fontFamily = poppinsFamily,
                fontSize = 16.sp,
                modifier = Modifier.clickable { onEntrarSemLogarClick() }
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
            .width(180.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFBD5A0D))
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
    QualABoaScreen(
        onCadastroClick = {},
        onLoginClick = {},
        onEntrarSemLogarClick = {}
    )
}
