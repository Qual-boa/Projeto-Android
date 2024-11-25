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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.ui.theme.BottomMenu
import com.example.qualaboaapp.ui.theme.CategoryItem
import com.example.qualaboaapp.ui.theme.Greeting
import com.example.qualaboaapp.ui.theme.PopularCategoryItem
import com.example.qualaboaapp.ui.theme.TopEstablishmentsCarousel
import com.example.qualaboaapp.ui.theme.home.categorias.CategoriesViewModel
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Saudação
            item {
                Greeting()
            }

            // Categorias
            item {
                Text(
                    text = "Categorias",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { category ->
                        CategoryItem(name = category.name)
                    }
                }
            }

            // Carrossel de Estabelecimentos
            item {
                TopEstablishmentsCarousel(viewModel = establishmentsViewModel)
            }

            // Categorias Populares
            item {
                Text(
                    text = "Categorias Populares",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(popularCategories) { category ->
                        PopularCategoryItem(name = category.name)
                    }
                }
            }
        }

        // Menu Inferior
        BottomMenu(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp)
        )
    }
}
