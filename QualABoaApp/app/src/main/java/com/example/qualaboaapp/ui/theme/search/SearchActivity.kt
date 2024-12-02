package com.example.qualaboaapp.ui.theme.search

import BarViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.accompanist.flowlayout.FlowRow
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.ContextCompat
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.home.getCategoryBackgroundColor
import com.example.qualaboaapp.ui.theme.home.getCategoryImage

class SearchActivity : ComponentActivity() {
    private val barViewModel: BarViewModel by viewModel()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                initializeLocationClientAndFetch()
            } else {
                android.util.Log.e("Permission", "Permissão de localização negada.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar e solicitar permissão
        if (hasLocationPermission()) {
            initializeLocationClientAndFetch()
        } else {
            requestLocationPermission()
        }

        setContent {
            SearchScreen(navController = rememberNavController(), viewModel = barViewModel, null)
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun initializeLocationClientAndFetch() {
        barViewModel.initializeLocationClient(this)
    }
}

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: BarViewModel,
    currentLocation: Location? // Localização passada diretamente
) {
    val context = LocalContext.current

    // Inicializa o cliente de localização apenas uma vez
    LaunchedEffect(Unit) {
        viewModel.initializeLocationClient(context)
        currentLocation?.let {
            viewModel.setUserLocation(it) // Atualiza o ViewModel com a localização atual
        }
    }

    val bars = viewModel.bars.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val barDistances = viewModel._barDistances.collectAsState() // Distâncias calculadas
    val establishmentPhotos by viewModel.establishmentPhotos.collectAsState(initial = emptyMap())

    // Categorias e Seleções
    val musics = listOf("Rock", "Sertanejo", "Indie", "Rap", "Funk", "Metal")
    val foods = listOf("Brasileira", "Boteco", "Japonesa", "Mexicana", "Churrasco", "Hamburguer")
    val drinks = listOf("Cerveja", "Vinho", "Chopp", "Whisky", "Gim", "Caipirinha", "Drinks")

    var selectedMusics by remember { mutableStateOf(emptyList<String>()) }
    var selectedFoods by remember { mutableStateOf(emptyList<String>()) }
    var selectedDrinks by remember { mutableStateOf(emptyList<String>()) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            PesquisaBar { searchText ->
                viewModel.updateSelectedMusics(selectedMusics)
                viewModel.updateSelectedFoods(selectedFoods)
                viewModel.updateSelectedDrinks(selectedDrinks)
                viewModel.fetchBarsWithDistances(searchText) // Busca bares com cálculo de distâncias
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = colorResource(R.color.brown_qab)
                )
            } else if (bars.value.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ClearButton { viewModel.clearBars() }

                BarList(
                    establishmentPhotos,
                    bars = bars.value,
                    navController = navController,
                    onFavoriteClick = { bar -> /* Lógica para favoritos */ },
                    distances = barDistances.value // Passa as distâncias calculadas
                )
                }
            } else {
                // Seletores de categoria
                CategorySelector(
                    categories = musics,
                    title = "Músicas",
                    selectedItems = selectedMusics,
                    onSelectionChange = { selectedMusics = it }
                )
                CategorySelector(
                    categories = foods,
                    title = "Comidas",
                    selectedItems = selectedFoods,
                    onSelectionChange = { selectedFoods = it }
                )
                CategorySelector(
                    categories = drinks,
                    title = "Bebidas",
                    selectedItems = selectedDrinks,
                    onSelectionChange = { selectedDrinks = it }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CategorySelector(
    categories: List<String>,
    title: String,
    selectedItems: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    Column {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            items(categories) { category -> // Use o "items" para cada item na lista
                val isSelected = selectedItems.contains(category)
                CategoryItem(
                    name = category,
                    isSelected = isSelected,
                    onClick = {
                        if (isSelected) {
                            onSelectionChange(selectedItems - category)
                        } else {
                            onSelectionChange(selectedItems + category)
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun CategoryItem(name: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
        // Colored box with the image
        Box(
            modifier = Modifier
                .size(60.dp)
                .then(
                    if (isSelected) {
                        Modifier.border(2.dp, colorResource(id = R.color.brown_qab))
                    } else {
                        Modifier // Sem borda se showBorder for false
                    }
                )
                .clip(RoundedCornerShape(10.dp))
                .background(getCategoryBackgroundColor(name))
                .clickable(onClick = onClick)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = getCategoryImage(name)),
                contentDescription = name,
                modifier = Modifier.size(40.dp)
            )
        }

        // Name outside the box
        androidx.compose.material3.Text(
            text = name,
            fontSize = 12.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun ClearButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .width(240.dp), // Tamanho fixo para manter o design consistente
        shape = RoundedCornerShape(24.dp), // Bordas arredondadas
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.brown_qab), // Cor de fundo (laranja)
            contentColor = Color.White // Cor do texto (preto)
        )
    ) {
        Text(
            text = "Limpar Pesquisa",
            fontSize = 16.sp, // Tamanho do texto
            fontWeight = FontWeight.Medium // Texto em negrito
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchScreen(navController = rememberNavController(), viewModel(), null)
}
