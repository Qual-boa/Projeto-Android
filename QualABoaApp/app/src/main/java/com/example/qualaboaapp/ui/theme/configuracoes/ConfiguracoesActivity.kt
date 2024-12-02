package com.example.qualaboaapp.ui.theme

import ConfiguracoesViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            ConfiguracoesScreen(koinViewModel(), "")
        }
    }
}

@Composable
fun ConfiguracoesScreen(
    configuracoesViewModel: ConfiguracoesViewModel = koinViewModel(),
    userId: String?
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Observa diretamente os fluxos do ViewModel
    val nome by configuracoesViewModel.nomeUsuario.collectAsState(initial = "")
    val email by configuracoesViewModel.emailUsuario.collectAsState(initial = "")
    val senha by configuracoesViewModel.senhaUsuario.collectAsState(initial = "")
    val isLoggedOut by configuracoesViewModel.isLoggedOut.collectAsState()

    // LaunchedEffect para carregar dados do usuário
    LaunchedEffect(userId) {
        Log.d("ConfiguracoesScreen", "UserId recebido: $userId")
        // Configure o ViewModel ou carregue dados específicos do usuário
        if (userId != null) {
            configuracoesViewModel.carregarDadosDoUsuario(userId)
        }
    }

// LaunchedEffect para redirecionar após logout
    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            Log.d("ConfiguracoesScreen", "Redirecionando após logout.")
            val intent = Intent(context, LoginCadastroInicialActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        // Botão de logout
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(onClick = {
                configuracoesViewModel.logout()
                Toast.makeText(context, "Deslogado com sucesso!", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(id = R.mipmap.sair),
                    contentDescription = stringResource(R.string.logout_button),
                    tint = Color.Black
                )
            }
        }

        // Imagem superior
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

        // Formulário de configuração
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
                    text = nome, // Fluxo direto do ViewModel
                    placeholder = "Digite seu nome",
                    onValueChange = { configuracoesViewModel.atualizarNome(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextFieldWithLabel(
                    label = "Email",
                    text = email, // Fluxo direto do ViewModel
                    placeholder = "Digite seu email",
                    onValueChange = { configuracoesViewModel.atualizarEmail(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextFieldWithLabel(
                    label = "Senha",
                    text = senha, // Fluxo direto do ViewModel
                    placeholder = "Digite sua senha",
                    isPassword = true,
                    onValueChange = { configuracoesViewModel.atualizarSenha(it) }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            configuracoesViewModel.atualizarPerfil(
                                name = nome,
                                email = email,
                                password = senha
                            )
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
    placeholder: String,
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
            placeholder = placeholder,
            isPassword = isPassword,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun ProfileTextFieldWithShadow(
    text: String,
    placeholder: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        // Sombra
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 4.dp, y = 4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFFBD5A0D))
        )

        // Campo principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(4.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                cursorBrush = SolidColor(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
            )

            // Placeholder exibido se o texto estiver vazio
            if (text.isEmpty()) {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                )
            }
        }
    }
}
