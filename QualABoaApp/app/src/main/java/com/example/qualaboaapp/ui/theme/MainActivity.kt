package com.example.qualaboaapp.ui.theme

import BarViewModel
import ConfiguracoesViewModel
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.Manifest // Certifique-se de usar o Manifest correto
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentScreen
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentViewModel
import com.example.qualaboaapp.ui.theme.favoritos.FavoriteScreen
import com.example.qualaboaapp.ui.theme.home.HomeScreen
import com.example.qualaboaapp.ui.theme.home.categorias.CategoriesViewModel
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentsViewModel
import com.example.qualaboaapp.ui.theme.search.SearchScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.qualaboaapp.ui.theme.home.BottomMenu
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhoto
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    private val categoriesViewModel: CategoriesViewModel by viewModel()
    private val establishmentsViewModel: EstablishmentsViewModel by viewModel()
    private val establishmentViewModel: EstablishmentViewModel by viewModel()
    private val barViewModel: BarViewModel by viewModel()
    private val configuracoesViewModel: ConfiguracoesViewModel by viewModel()
    private val _userId :String = ""

    var currentLocation by mutableStateOf<Location?>(null) // Alterado para MutableState
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            Log.d("Permission", "Permissões concedidas!")
            getLastLocation()
        } else {
            Log.e("Permission", "Permissões negadas!")
            Toast.makeText(
                this,
                "Permissão de localização é necessária para o aplicativo funcionar.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getLastLocation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("USER_ID") ?: ""
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermissions()
        setContent {
            MainScreen(
                categoriesViewModel,
                establishmentsViewModel,
                establishmentViewModel,
                barViewModel,
                configuracoesViewModel,
                currentLocation,
                userId
            )
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("Location", "Permissões não concedidas")
            return
        }

        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                barViewModel.setUserLocation(currentLocation!!) // Atualiza o ViewModel
                Log.d("Location", "LOCALIZADO: $currentLocation")
            } else {
                Log.d("Location", "Localização está nula")
                Toast.makeText(this, "Não foi possível obter a localização", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    categoriesViewModel: CategoriesViewModel,
    establishmentsViewModel: EstablishmentsViewModel,
    establishmentViewModel: EstablishmentViewModel,
    barViewModel: BarViewModel,
    configuracoesViewModel: ConfiguracoesViewModel,
    currentLocation: Location?,
    userId: String
) {
    val navController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        bottomBar = { BottomMenu(navController) }
    ) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    null,
                    categoriesViewModel,
                    establishmentsViewModel,
                    navController
                )
            }
            composable("pesquisa") {
                barViewModel.clearBars()
                if (currentLocation != null) {
                    SearchScreen(
                        navController = navController,
                        barViewModel,
                        currentLocation,
                        userId = userId
                    )
                } else {
                    Toast.makeText(context, "Localização ainda não obtida", Toast.LENGTH_SHORT).show()
                }
            }
            composable("favoritos") {
                FavoriteScreen(
                    navController = navController,
                    viewModel = barViewModel,
                    userId = userId // Passe o ID do usuário
                )
            }

            composable("perfil") {
                ConfiguracoesScreen(
                    configuracoesViewModel = configuracoesViewModel,
                    userId = userId // Passa o userId
                )
            }
            composable(
                route = "estabelecimento/{id}?photos={photos}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    },
                    navArgument("photos") {
                        type = NavType.StringType
                        nullable = true // Argumento opcional
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                val photosJson = backStackEntry.arguments?.getString("photos")

                // Desserializar a lista de objetos (se disponível)
                val photosList: List<EstablishmentPhoto>? = photosJson?.let {
                    Gson().fromJson(it, object : TypeToken<List<EstablishmentPhoto>>() {}.type)
                }

                EstablishmentScreen(
                    id = id,
                    photo = photosList,
                    viewModel = establishmentViewModel,
                    navController = navController,
                    context = context
                )
            }
        }
    }
}

