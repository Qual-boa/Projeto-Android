package com.example.qualaboaapp.ui.theme

import ConfiguracoesViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.pagina_inicial.LoginCadastroInicialActivity
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ConfiguracoesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfiguracoesScreen()
        }
    }
}

@Composable
fun ConfiguracoesScreen(
    configuracoesViewModel: ConfiguracoesViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        configuracoesViewModel.nomeUsuario.collect { nome = it }
    }
    LaunchedEffect(Unit) {
        configuracoesViewModel.emailUsuario.collect { email = it }
    }
    LaunchedEffect(Unit) {
        configuracoesViewModel.senhaUsuario.collect { senha = it }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        // Logout Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(onClick = {
                configuracoesViewModel.logout()
                Toast.makeText(context, "Deslogado com sucesso!", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, LoginCadastroInicialActivity::class.java))
            }) {
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                ProfileTextFieldWithLabel(
                    label = "Nome",
                    text = nome,
                    onValueChange = { nome = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextFieldWithLabel(
                    label = "Email",
                    text = email,
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextFieldWithLabel(
                    label = "Senha",
                    text = senha,
                    isPassword = true,
                    onValueChange = { senha = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            configuracoesViewModel.atualizarPerfil(nome, email, senha)
                            Toast.makeText(context, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA1530A))
                ) {
                    Text(
                        text = stringResource(R.string.salvar_button),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileTextFieldWithLabel(
    label: String,
    text: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )
        ProfileTextFieldWithShadow(
            text = text,
            isPassword = isPassword,
            onValueChange = onValueChange
        )
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
        // Sombra do campo de entrada
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 4.dp, y = 4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFFBD5A0D))
        )

        // Campo de entrada principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(4.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = { onValueChange(it) },
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
            )
        }
    }
}
