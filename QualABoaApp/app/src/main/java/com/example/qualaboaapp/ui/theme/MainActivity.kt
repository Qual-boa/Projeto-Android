package com.example.qualaboaapp.ui.theme

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
import com.example.qualaboaapp.ui.theme.favoritos.FavoriteScreen
import com.example.qualaboaapp.ui.theme.home.HomeScreen
import com.example.qualaboaapp.ui.theme.notificacoes.NotificationScreen
import com.example.qualaboaapp.ui.theme.search.SearchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomMenu(navController) } // Passa o NavController
    ) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") { HomeScreen() }
            composable("notificacoes") { NotificationScreen() }
            composable("pesquisa") { SearchScreen(navController = navController) }
            composable("favoritos") { FavoriteScreen() }
            composable("estabelecimento") { EstablishmentScreen() }
//            composable("perfil") { ProfileScreen() }
        }
    }
}
