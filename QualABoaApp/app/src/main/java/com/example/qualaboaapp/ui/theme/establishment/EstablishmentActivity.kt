package com.example.qualaboaapp.ui.theme.establishment

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EstablishmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: EstablishmentViewModel by viewModel() // Usando Koin para injetar o ViewModel
        super.onCreate(savedInstanceState)
        val establishmentId = intent.getStringExtra("id") ?: ""
        setContent {
            val navController = rememberNavController()
            EstablishmentScreen(
                id = establishmentId,
                viewModel = viewModel, // Koin ViewModel Injection
                navController = navController,
                context = this // Passa o contexto atual
            )
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun EstablishmentScreen(
    id: String,
    viewModel: EstablishmentViewModel,
    navController: NavHostController,
    context: Context // Adicionado para recuperar a chave da API
) {
    val establishment = viewModel.establishment.collectAsState()
    val address = viewModel.address.collectAsState()
    val coordinates = viewModel.coordinates.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    // Atualiza os dados sempre que o ID do bar mudar
    LaunchedEffect(id) {
        viewModel.loadEstablishmentById(id)
        viewModel.loadAddressAndCoordinates(id, context)
    }

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        establishment.value?.let { est ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Avaliações
                ReviewSection(reviews = est.relationships ?: emptyList())

                Spacer(modifier = Modifier.height(16.dp))

                // Verifica se coordenadas válidas estão disponíveis
                if (coordinates.value != null) {
                    LocationSection(
                        latitude = coordinates.value!!.latitude,
                        longitude = coordinates.value!!.longitude,
                        address = "${address.value?.street}, ${address.value?.number}, ${address.value?.city}, ${address.value?.state}, ${address.value?.postalCode}",
                        onShareLocation = {
                            // Lógica para compartilhar a localização
                        }
                    )
                } else {
                    Text(
                        text = "Localização não disponível",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Outros componentes, como galeria, categorias, etc.
            }
        } ?: Text(
            text = "Estabelecimento não encontrado",
            modifier = Modifier.padding(16.dp)
        )
    }
}

fun getGoogleApiKey(context: Context): String {
    val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
    return appInfo.metaData.getString("com.google.android.geo.API_KEY")
        ?: throw IllegalStateException("Google API Key not found in AndroidManifest.xml")
}

@Preview(showBackground = true)
@Composable
fun PreviewEstablishmentScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    // Parâmetros simulados para o preview
    EstablishmentScreen(
        id = "dummy-id",
        viewModel = viewModel(),
        navController = rememberNavController(),
        context = context
    )
}