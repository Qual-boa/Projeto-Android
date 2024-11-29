package com.example.qualaboaapp.ui.theme

import BarViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentScreen
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentViewModel
import com.example.qualaboaapp.ui.theme.favoritos.FavoriteScreen
import com.example.qualaboaapp.ui.theme.home.BottomMenu
import com.example.qualaboaapp.ui.theme.home.HomeScreen
import com.example.qualaboaapp.ui.theme.home.categorias.CategoriesViewModel
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentsViewModel
import com.example.qualaboaapp.ui.theme.notificacoes.NotificationScreen
import com.example.qualaboaapp.ui.theme.search.SearchScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val categoriesViewModel: CategoriesViewModel by viewModel()
    private val establishmentsViewModel: EstablishmentsViewModel by viewModel()
    private val establishmentViewModel: EstablishmentViewModel by viewModel()
    private val barViewModel : BarViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                categoriesViewModel,
                establishmentsViewModel,
                establishmentViewModel,
                barViewModel)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(categoriesViewModel: CategoriesViewModel,
               establishmentsViewModel: EstablishmentsViewModel,
               establishmentViewModel: EstablishmentViewModel,
               barViewModel: BarViewModel
               ) {
    val navController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        bottomBar = { BottomMenu(navController) } // Passa o NavController
    ) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") { HomeScreen(
                null,
                categoriesViewModel,
                establishmentsViewModel,
                navController)
            }
            composable("notificacoes") { NotificationScreen() }
            composable("pesquisa") {
                barViewModel.clearBars()
                SearchScreen(navController = navController, barViewModel)
            }
            composable("favoritos") { FavoriteScreen() }
            composable("estabelecimento/{id}") {  backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""

                EstablishmentScreen(
                    id = id,
                    viewModel = establishmentViewModel,
                    navController = navController,
                    context = context
                )
             }
//            composable("perfil") { ProfileScreen() }
        }
    }
}
