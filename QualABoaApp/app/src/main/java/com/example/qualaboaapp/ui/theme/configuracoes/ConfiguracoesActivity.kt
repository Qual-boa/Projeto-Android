package com.example.qualaboaapp.ui.theme

import ConfiguracoesViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.pagina_inicial.LoginCadastroInicialActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

val PoppinsFont = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

class ConfiguracoesActivity : ComponentActivity() {

    private val configuracoesViewModel: ConfiguracoesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            var nome by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var senha by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                configuracoesViewModel.nomeUsuario.collectLatest { nome = it }
            }
            LaunchedEffect(Unit) {
                configuracoesViewModel.emailUsuario.collectLatest { email = it }
            }
            LaunchedEffect(Unit) {
                configuracoesViewModel.senhaUsuario.collectLatest { senha = it }
            }

            ProfileScreen(
                nome = nome,
                email = email,
                senha = senha,
                onNomeChange = { nome = it },
                onEmailChange = { email = it },
                onSenhaChange = { senha = it },
                onSaveClick = {
                    coroutineScope.launch {
                        configuracoesViewModel.atualizarPerfil(nome, email, senha)
                        val successMessage = context.getString(R.string.perfil_atualizado_sucesso)
                        Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                    }
                },
                onLogoutClick = {
                    configuracoesViewModel.logout()
                    val logoutMessage = context.getString(R.string.deslogado_sucesso)
                    Toast.makeText(context, logoutMessage, Toast.LENGTH_SHORT).show()
                    context.startActivity(Intent(context, LoginCadastroInicialActivity::class.java))
                    finish()
                }
            )
        }
    }
}
@Composable
fun ProfileTextFieldWithShadow(
    text: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
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
                    value = text,
                    onValueChange = { onValueChange(it) },
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.poppins_regular, FontWeight.Normal)
                        )
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                        .weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.mipmap.edit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Composable
fun ProfileScreen(
    nome: String,
    email: String,
    senha: String,
    onNomeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSenhaChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(onClick = { onLogoutClick() }) {
                Icon(
                    painter = painterResource(id = R.mipmap.sair),
                    contentDescription = stringResource(R.string.logout_button),
                    tint = Color.Black
                )
            }
        }

        Image(
            painter = painterResource(id = R.mipmap.circulo),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopCenter)
                .offset(y = 20.dp)
                .zIndex(0f)
        )

        Image(
            painter = painterResource(id = R.mipmap.icone),
            contentDescription = stringResource(R.string.user_icon_description),
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
                    text = stringResource(R.string.perfil_titulo),
                    fontFamily = PoppinsFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                ProfileTextFieldWithShadow(text = nome, onValueChange = onNomeChange)
                Spacer(modifier = Modifier.height(16.dp))
                ProfileTextFieldWithShadow(text = email, onValueChange = onEmailChange)
                Spacer(modifier = Modifier.height(16.dp))
                ProfileTextFieldWithShadow(text = senha, isPassword = true, onValueChange = onSenhaChange)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onSaveClick() },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA1530A))
                ) {
                    Text(
                        text = stringResource(R.string.salvar_button),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFont
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

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileScreen(
        nome = "Matheus",
        email = "matheus@email.com",
        senha = "*******",
        onNomeChange = {},
        onEmailChange = {},
        onSenhaChange = {},
        onSaveClick = {},
        onLogoutClick = {}
    )
}
