package com.example.qualaboaapp.ui.theme.favoritos

import BarViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.notificacoes.NotificationCard
import com.example.qualaboaapp.ui.theme.search.BarList
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritosActivity : ComponentActivity() {
    private val barViewModel: BarViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            FavoriteScreen(
                navController = rememberNavController(),
                viewModel = barViewModel,
                ""
            )
        }
    }
}

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: BarViewModel,
    userId: String?
) {
    val context = LocalContext.current

    // Chama o método para buscar os favoritos ao abrir a tela
    LaunchedEffect(userId) {
        viewModel.fetchFavoriteBars(userId) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    // Observa os estados
    val bars = viewModel.bars.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val establishmentPhotos by viewModel.establishmentPhotos.collectAsState(initial = emptyMap())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Meus Favoritos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = colorResource(R.color.brown_qab)
                )
            } else if (bars.value.isNotEmpty()) {
                BarList(
                    establishmentPhotos = establishmentPhotos,
                    bars = bars.value,
                    navController = navController,
                    favorites = bars.value.map { it.id },
                    onFavoriteClick = { bar ->
                        viewModel.toggleFavorite(
                            establishmentId = bar.id,
                            userId = userId,
                            onSuccess = { message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    distances = emptyMap()
                )
            } else {
                Text(
                    text = "Nenhum favorito encontrado.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FavoriteScreen(
        navController = rememberNavController(),
        viewModel = viewModel(),
        ""
    )
}
