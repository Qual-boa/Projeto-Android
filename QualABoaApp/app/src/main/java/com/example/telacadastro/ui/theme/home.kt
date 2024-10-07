package com.example.telacadastro.ui.theme

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.telacadastro.R

class HomeActivity : ComponentActivity() {

    private val poppinsFamily = FontFamily(
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_black, FontWeight.Black),
        Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
        Font(R.font.poppins_blackitalic, FontWeight.Black)
    )

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            TelaCadastroTheme {
                HomeScreen { getLocation() }
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    // Use the location object (latitude and longitude)
                }
            }
        }
    }

    private val requestLocationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLocation()
        }
    }
}

@Composable
fun HomeScreen(getLocation: () -> Unit) {
    var address by remember { mutableStateOf("São Paulo, Vila Madalena") }
    var showLocationModal by remember { mutableStateOf(false) }
    var manualLocation by remember { mutableStateOf("") }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    // Modal para localização
    if (showLocationModal) {
        Dialog(onDismissRequest = { showLocationModal = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Escolha a localização", fontWeight = FontWeight.Bold, fontFamily = poppinsFamily)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        getLocation()  // Utiliza a localização atual
                        showLocationModal = false
                    }) {
                        Text(text = "Usar localização atual", fontFamily = poppinsFamily)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = manualLocation,
                        onValueChange = { manualLocation = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (manualLocation.isEmpty()) {
                                Text("Insira a localização manualmente", fontFamily = poppinsFamily)
                            }
                            innerTextField()
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        if (manualLocation.isNotEmpty()) {
                            address = manualLocation
                        }
                        showLocationModal = false
                    }) {
                        Text(text = "Confirmar localização manual", fontFamily = poppinsFamily)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Seção do Endereço
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = address, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsFamily)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Dropdown",
                modifier = Modifier.clickable { showLocationModal = true }
            )
        }

        // Barra de Pesquisa
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                textStyle = LocalTextStyle.current.copy(fontFamily = poppinsFamily)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Filter",
                modifier = Modifier.clickable { /* Abrir modal de filtro */ }
            )
        }

        // Carrossel de Categorias
        Text(
            text = "Categorias",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = poppinsFamily
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 16.dp)
        ) {
            // Exemplo de itens de categoria
            CategoryItem("Rock", R.mipmap.logo)
            CategoryItem("Vinho", R.mipmap.logo)
            CategoryItem("Vegano", R.mipmap.logo)
            CategoryItem("Boteco", R.mipmap.logo)
        }

        // Carrossel de Estabelecimentos mais procurados
        Text(
            text = "Estabelecimentos mais procurados",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = poppinsFamily
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 16.dp)
        ) {
            // Exemplo de itens de estabelecimento
            EstablishmentItem("Beer4U", R.mipmap.logo)
            EstablishmentItem("Bar do Dudu", R.mipmap.logo)
        }

        // Carrossel Baseado nas suas Boas
        Text(
            text = "Baseado nas suas Boas",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = poppinsFamily
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 16.dp)
        ) {
            // Exemplo de itens baseados nas preferências
            PreferenceItem("Bar do Samuca", R.mipmap.logo)
            PreferenceItem("Bar do Samuca", R.mipmap.logo)
        }

        // Carrossel de Comidas Populares
        Text(
            text = "Comidas Populares",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = poppinsFamily
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 16.dp)
        ) {
            // Exemplo de comidas populares
            PopularFoodItem("Pizza", R.mipmap.logo)
            PopularFoodItem("Hambúrguer", R.mipmap.logo)
        }

        // Menu Fixo Inferior
        HomeBottomMenu()
    }
}

@Composable
fun CategoryItem(name: String, iconRes: Int) {
    Column(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = name)
        Text(text = name, fontFamily = poppinsFamily)
    }
}

@Composable
fun EstablishmentItem(name: String, imageRes: Int) {
    Column(
        modifier = Modifier
            .padding(end = 8.dp)
    ) {
        Image(painter = painterResource(id = imageRes), contentDescription = name)


        Text(text = name)
    }
}

@Composable
fun PreferenceItem(name: String, imageRes: Int) {
    Column(
        modifier = Modifier
            .padding(end = 8.dp)
    ) {
        Image(painter = painterResource(id = imageRes), contentDescription = name)
        Text(text = name)
    }
}

@Composable
fun PopularFoodItem(name: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier.size(60.dp)
        )
        Text(text = name)
    }
}

@Composable
fun HomeBottomMenu() {
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(56.dp)
    ) {
        BottomNavigationItem(
            icon = {
                Icon(painter = painterResource(id = R.mipmap.logo), contentDescription = "Home") },
            selected = true,
            onClick = { /* Navegar para home */ }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.mipmap.logo), contentDescription = "Notifications") },
            selected = false,
            onClick = { /* Navegar para notificações */ }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.mipmap.logo), contentDescription = "Search") },
            selected = false,
            onClick = {  }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.mipmap.logo), contentDescription = "Favorites") },
            selected = false,
            onClick = {  }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.mipmap.logo), contentDescription = "User") },
            selected = false,
            onClick = {  }
        )
    }
}

// Preview da Tela Home
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(getLocation = {})
}
