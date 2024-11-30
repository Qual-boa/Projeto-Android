package com.example.qualaboaapp.ui.theme.home

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentViewModel
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.BottomMenu
import com.example.qualaboaapp.ui.theme.CategoryItem
import com.example.qualaboaapp.ui.theme.EstablishmentCard
import com.example.qualaboaapp.ui.theme.EstablishmentCarouselItem
import com.example.qualaboaapp.ui.theme.Greeting
import com.example.qualaboaapp.ui.theme.PopularCategoryItem
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
                    establishmentsViewModel = establishmentsViewModel,
                    navController = rememberNavController()
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    username: String?,
    categoriesViewModel: CategoriesViewModel,
    establishmentsViewModel: EstablishmentsViewModel,
    navController: NavController
) {
    val categories by categoriesViewModel.categories.collectAsState(initial = emptyList())
    val popularCategories by categoriesViewModel.popularCategories.collectAsState(initial = emptyList())
    val topEstablishments by establishmentsViewModel.topEstablishments.collectAsState(initial = emptyList())
    val establishmentPhotos by establishmentsViewModel.establishmentPhotos.collectAsState(initial = emptyMap())
    val bestCategoryEstablishments by establishmentsViewModel.bestCategoryEstablishments.collectAsState(initial = emptyList())

    LaunchedEffect(popularCategories) {
        if (popularCategories.isNotEmpty()) {
            establishmentsViewModel.loadBestCategoryEstablishments(popularCategories)
        }
    }

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
                    text = stringResource(R.string.categories_title),
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

            // Título e Conteúdo: Top 5 Melhores Estabelecimentos
            item {
                Text(
                    text = stringResource(R.string.top_establishments_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(topEstablishments) { establishment ->
                        val photos = establishmentPhotos[establishment.id] ?: emptyList()
                        EstablishmentCarouselItem(establishment = establishment, photos = photos)
                    }
                }
            }

            // Categorias Populares
            item {
                Text(
                    text = stringResource(R.string.popular_categories_title),
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

            // Baseado nas melhores categorias
            item {
                Text(
                    text = stringResource(R.string.based_on_popular_categories),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                if (bestCategoryEstablishments.isNotEmpty()) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(bestCategoryEstablishments) { establishment ->
                            val photos = establishmentPhotos[establishment.id] ?: emptyList()
                            EstablishmentCard(establishment = establishment, photos = photos)
                        }
                    }
                }
            }
        }
    }
}
