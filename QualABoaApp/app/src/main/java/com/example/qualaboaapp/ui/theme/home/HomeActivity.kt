package com.example.qualaboaapp.ui.theme.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.qualaboaapp.ui.theme.BottomMenu
import com.example.qualaboaapp.ui.theme.CategoryItem
import com.example.qualaboaapp.ui.theme.EstablishmentCard
import com.example.qualaboaapp.ui.theme.Greeting
import com.example.qualaboaapp.ui.theme.PoppinsFont
import com.example.qualaboaapp.ui.theme.TopEstablishmentsScreen
import com.example.qualaboaapp.ui.theme.home.categorias.CategoriesViewModel
import com.example.qualaboaapp.ui.theme.home.categorias.categoriesModule
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentsViewModel
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.establishmentsModule
import com.example.qualaboaapp.ui.theme.login.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.startKoin

class HomeActivity : ComponentActivity() {

    private val categoriesViewModel: CategoriesViewModel by viewModel()
    private val establishmentsViewModel: EstablishmentsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                HomeScreen(
                    username = "Usuário",
                    categoriesViewModel = categoriesViewModel,
                    establishmentsViewModel = establishmentsViewModel
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    username: String?,
    categoriesViewModel: CategoriesViewModel,
    establishmentsViewModel: EstablishmentsViewModel
) {
    val categories by categoriesViewModel.categories.collectAsState(initial = emptyList())
    val popularCategories by categoriesViewModel.popularCategories.collectAsState(initial = emptyList())
    val topEstablishments by establishmentsViewModel.topEstablishments.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // Space for the BottomMenu
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Greeting
            item {
                Greeting()
            }

            // Categories Section
            item {
                Text(
                    text = "Categorias",
                    fontFamily = PoppinsFont,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    items(categories) { category ->
                        CategoryItem(name = category.name)
                    }
                }
            }

            // Establishments Section
            item {
                Text(
                    text = "Top 5 Melhores Estabelecimentos",
                    fontFamily = PoppinsFont,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }

            // List of Establishments
            items(topEstablishments) { establishment ->
                val photos = establishmentsViewModel.establishmentPhotos.collectAsState().value[establishment.id] ?: emptyList()
                EstablishmentCard(establishment = establishment, photos = photos)
            }

            // Popular Categories Section (below Establishments)
            item {
                Text(
                    text = "Categorias Populares",
                    fontFamily = PoppinsFont,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    items(popularCategories) { category ->
                        CategoryItem(name = category.name)
                    }
                }
            }
        }

        // Bottom Menu
        BottomMenu(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp)
        )
    }
}
