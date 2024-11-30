package com.example.qualaboaapp.ui.theme.cadastro

import PoppinsFont
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.login.InputFieldWithShadow
import com.example.qualaboaapp.ui.theme.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroActivity : ComponentActivity() {

    private val cadastroViewModel: CadastroViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var nomeState by remember { mutableStateOf("") }
            var emailState by remember { mutableStateOf("") }
            var senhaState by remember { mutableStateOf("") }

            var nomeError by remember { mutableStateOf<String?>(null) }
            var emailError by remember { mutableStateOf<String?>(null) }
            var senhaError by remember { mutableStateOf<String?>(null) }

            val cadastroStatus by cadastroViewModel.cadastroStatus.collectAsState(initial = null)
            val erroCadastro by cadastroViewModel.erroCadastro.collectAsState(initial = null)

            LaunchedEffect(cadastroStatus) {
                cadastroStatus?.let { isSuccess ->
                    if (isSuccess) {
                        Toast.makeText(
                            this@CadastroActivity,
                            getString(R.string.cadastro_sucesso),
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@CadastroActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@CadastroActivity,
                            erroCadastro ?: getString(R.string.cadastro_erro),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            RegistrationScreen(
                nome = nomeState,
                email = emailState,
                senha = senhaState,
                nomeError = nomeError,
                emailError = emailError,
                senhaError = senhaError,
                onNomeChange = {
                    nomeState = it
                    nomeError = if (it.isBlank()) getString(R.string.nome_vazio_erro) else null
                },
                onEmailChange = {
                    emailState = it
                    emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                        getString(R.string.email_invalido_erro)
                    } else null
                },
                onSenhaChange = {
                    senhaState = it
                    senhaError = if (it.length < 8) getString(R.string.senha_curta_erro) else null
                },
                onCadastrarClick = {
                    if (nomeError == null && emailError == null && senhaError == null) {
                        cadastroViewModel.cadastrarUsuario(
                            nome = nomeState.ifEmpty { "Usuário" },
                            email = emailState,
                            senha = senhaState
                        )
                    } else {
                        Toast.makeText(
                            this@CadastroActivity,
                            getString(R.string.corrigir_erros_antes_de_prosseguir),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        }
    }
}

@Composable
fun RegistrationScreen(
    nome: String,
    email: String,
    senha: String,
    nomeError: String?,
    emailError: String?,
    senhaError: String?,
    onNomeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSenhaChange: (String) -> Unit,
    onCadastrarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagem de fundo
        Image(
            painter = painterResource(id = R.mipmap.backgroundd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Conteúdo principal
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFF3E0))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.cadastro_titulo),
                        fontFamily = PoppinsFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputFieldWithShadow(
                        label = stringResource(id = R.string.nome_label),
                        text = nome,
                        onTextChange = onNomeChange
                    )
                    if (nomeError != null) {
                        Text(
                            text = nomeError,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InputFieldWithShadow(
                        label = stringResource(id = R.string.email_label),
                        text = email,
                        onTextChange = onEmailChange
                    )
                    if (emailError != null) {
                        Text(
                            text = emailError,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InputFieldWithShadow(
                        label = stringResource(id = R.string.password_label),
                        text = senha,
                        isPassword = true,
                        onTextChange = onSenhaChange
                    )
                    if (senhaError != null) {
                        Text(
                            text = senhaError,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = onCadastrarClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBD5A0D)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.register_button),
                            fontFamily = PoppinsFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
