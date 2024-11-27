package com.example.qualaboaapp.ui.theme.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.PoppinsFont
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : ComponentActivity() {
    private val barViewModel : BarViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen(navController = rememberNavController(), viewModel = barViewModel)
        }
    }
}

@Composable
fun SearchScreen(navController: NavController, viewModel: BarViewModel) {
    val bars = viewModel.bars.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.qual_a_boa_de_hoje),
                fontFamily = PoppinsFont,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de pesquisa
            PesquisaBar { searchText ->
                viewModel.searchBars(searchText) // Chama a API ao clicar no botão de pesquisa
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Seção de categorias
            Text(
                text = stringResource(R.string.categorias), // Exibe o título "Categorias"
                fontFamily = PoppinsFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            CategoriasGrid(navController) // Agora a seção de categorias será chamada

            Spacer(modifier = Modifier.height(16.dp))

            // Bares carregados da API
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (bars.value.isNotEmpty()) {
                BarList(bars.value) { clickedBar ->
                    // Lógica para favoritos ou outras ações
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFFFFF1D5))
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchScreen(navController = rememberNavController(), viewModel())
}
