package com.example.qualaboaapp.ui.theme.establishment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhoto
import com.example.qualaboaapp.ui.theme.search.createAmenitiesString

class EstablishmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: EstablishmentViewModel by viewModel() // Usando Koin para injetar o ViewModel
        super.onCreate(savedInstanceState)
        val establishmentId = intent.getStringExtra("id") ?: ""
        setContent {
            val navController = rememberNavController()
            EstablishmentScreen(
                id = establishmentId,
                photo = emptyList(),
                viewModel = viewModel, // Koin ViewModel Injection
                navController = navController,
                context = this // Passa o contexto atual
            )
        }
    }
}

@Composable
fun EstablishmentScreen(
    id: String,
    photo: List<EstablishmentPhoto>?,
    viewModel: EstablishmentViewModel,
    navController: NavHostController,
    context: Context
) {
    val establishment = viewModel.establishment.collectAsState()
    val address = viewModel.address.collectAsState()
    val coordinates = viewModel.coordinates.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val reviewsWithUsers = viewModel.reviewsWithUsers.collectAsState(initial = emptyList())

    LaunchedEffect(id) {
        viewModel.loadEstablishmentById(id)
        viewModel.loadAddressAndCoordinates(id, context)
    }

    establishment.value?.relationships?.let { relationships ->
        // Load reviews when relationships are available
        LaunchedEffect(relationships) {
            viewModel.loadReviewsWithUsers(relationships)
        }
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // Header Image
                item {
                    if (photo != null) {
                        Image(
                            painter = rememberAsyncImagePainter(photo.filter { it.establishmentCategory == "BACKGROUND" }.first().imgUrl),
                            contentDescription = "Imagem de Capa",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Info Section
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFDBB27)) // Sem arredondamento
                            .padding(16.dp)
                    ) {
                        Column {
                            // Linha com o texto e os ícones
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween, // Espaça os elementos nas extremidades
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = est.fantasyName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                                // Ícones de redes sociais
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    est.information.facebookUrl?.let { url ->
                                        SocialMediaIcon(
                                            icon = R.drawable.facebook,
                                            description = "Facebook",
                                            url = url,
                                            context = context
                                        )
                                    }
                                    est.information.instagramUrl?.let { url ->
                                        SocialMediaIcon(
                                            icon = R.drawable.instagram,
                                            description = "Instagram",
                                            url = url,
                                            context = context
                                        )
                                    }
                                    est.information.telegramUrl?.let { url ->
                                        SocialMediaIcon(
                                            icon = R.drawable.telegram,
                                            description = "Telegram",
                                            url = url,
                                            context = context
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = rememberAsyncImagePainter(photo?.first()?.imgUrl),
                                    contentDescription = "Logo",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        repeat(5) { // Exemplo de estrelas fixas
                                            Icon(
                                                painter = painterResource(id = R.drawable.stars_icon),
                                                contentDescription = "Star",
                                                tint = Color.Black,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = est.information.description,
                                        color = Color.DarkGray,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = formatOperationHours(
                                            est.information.openAt,
                                            est.information.closeAt
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                    Text(
                                        text = "CONTATO: ${est.information.phone}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Reviews Section
                item {
                    Text(
                        text = "Avaliações",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    ReviewSection(reviews = reviewsWithUsers.value)
                }

                // Location Section
                item {
                    if (coordinates.value != null) {
                        LocationSection(
                            latitude = coordinates.value?.latitude ?: 0.0,
                            longitude = coordinates.value?.longitude ?: 0.0,
                            address = "${address.value?.street}, ${address.value?.number}, ${address.value?.city}, ${address.value?.state}, ${address.value?.postalCode}",
                            context = context
                        )
                    } else {
                        Text(
                            text = "Localização não disponível",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    val filteredList = photo?.filter {
                        it.establishmentCategory == "MENU" || it.establishmentCategory == "GALLERY"
                    }?.sortedByDescending {
                        it.establishmentCategory == "MENU"
                    }

                    // Galeria com Carrossel
                    Text(
                        text = "Galeria",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    GallerySection(filteredList)

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } ?: Text(
            text = "Estabelecimento não encontrado",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun SocialMediaIcon(icon: Int, description: String, url: String, context: Context) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = description,
        tint = Color.Unspecified, // Mantém a cor original do ícone
        modifier = Modifier
            .size(24.dp)
            .clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://${url}") // Define o URL como destino
                    }
                    context.startActivity(intent) // Inicia o navegador ou aplicativo
                } catch (e: Exception) {
                    Toast.makeText(context, "Não foi possível abrir o link", Toast.LENGTH_SHORT).show()
                }
            }
    )
}


fun getGoogleApiKey(context: Context): String {
    val appInfo = context.packageManager.getApplicationInfo(
        context.packageName,
        PackageManager.GET_META_DATA
    )
    return appInfo.metaData.getString("com.google.android.geo.API_KEY")
        ?: throw IllegalStateException("Google API Key not found in AndroidManifest.xml")
}

fun formatOperationHours(openAt: List<Int>, closeAt: List<Int>): String {
    // Validar os tamanhos da lista
    if (openAt.size != 2 || closeAt.size != 2) {
        return "Horário inválido"
    }

    // Formatar os horários no formato "hh:mm"
    val openTime = String.format("%02d:%02d", openAt[0], openAt[1])
    val closeTime = String.format("%02d:%02d", closeAt[0], closeAt[1])

    // Retornar a string formatada
    return "FUNCIONAMENTO: seg a dom das $openTime às $closeTime"
}

@Preview(showBackground = true)
@Composable
fun PreviewEstablishmentScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    // Parâmetros simulados para o preview
    EstablishmentScreen(
        id = "dummy-id",
        photo = emptyList(),
        viewModel = viewModel(),
        navController = rememberNavController(),
        context = context
    )
}