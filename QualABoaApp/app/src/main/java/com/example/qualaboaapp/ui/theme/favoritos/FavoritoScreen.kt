import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.ui.theme.utils.NotificationCard
import org.koin.androidx.compose.getViewModel
import com.example.qualaboaapp.R

@Composable
fun FavoriteScreen(viewModel: FavoritesViewModel = getViewModel()) {
    val favorites by viewModel.favorites.collectAsState()

    // Inicia a chamada ao carregar a tela
    LaunchedEffect(Unit) {
        viewModel.fetchFavorites("USER_ID_AQUI")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.favoritos),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            // Renderizar dinamicamente os favoritos
            favorites.forEach { favorite ->
                NotificationCard(
                    imageRes = R.mipmap.perfil,
                    title = favorite.fantasyName,
                    description = favorite.information.description,
                    services = listOfNotNull(
                        if (favorite.information.hasParking) "Estacionamento" else null,
                        if (favorite.information.hasAccessibility) "Acessibilidade" else null,
                        if (favorite.information.hasTv) "TV" else null,
                        if (favorite.information.hasWifi) "Wi-Fi" else null
                    ),
                    isFavorite = true,
                    onFavoriteClick = {
                        println("Favorito clicado: ${favorite.id}")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.favorites_loaded_message))
                }
            }
        }
    }
}
