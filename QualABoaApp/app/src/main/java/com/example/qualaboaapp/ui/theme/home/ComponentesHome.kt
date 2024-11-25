    package com.example.qualaboaapp.ui.theme

    import android.annotation.SuppressLint
    import com.example.qualaboaapp.ui.theme.search.SearchActivity
    import android.content.Intent
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.LazyRow
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Card
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import coil.compose.rememberAsyncImagePainter
    import com.example.qualaboaapp.R
    import com.example.qualaboaapp.ui.theme.favoritos.FavoritosActivity
    import com.example.qualaboaapp.ui.theme.home.HomeActivity
    import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.Establishment
    import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhoto
    import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentsViewModel
    import com.example.qualaboaapp.ui.theme.notificacoes.NotificacaoActivity
    import com.example.qualaboaapp.ui.theme.utils.UserPreferences
    import org.koin.androidx.compose.get

    @Composable
    fun Greeting() {
        val userPreferences: UserPreferences = get() // Inject UserPreferences
        val isLoggedIn by userPreferences.isLoggedIn.collectAsState(initial = false)
        val userName by userPreferences.userName.collectAsState(initial = null)

        Text(
            text = if (isLoggedIn && !userName.isNullOrBlank()) "Olá, $userName!" else "Olá!",
            color = Color(0xFFA1530A),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

    fun getCategoryImage(name: String): Int {
        return when (name) {
            "Rock" -> R.mipmap.rock
            "Sertanejo" -> R.mipmap.sertanejo
            "Indie" -> R.mipmap.indie
            "Rap" -> R.mipmap.rap
            "Funk" -> R.mipmap.funk
            "Metal" -> R.mipmap.padrao
            "Brasileira" -> R.mipmap.brasileira
            "Japonesa" -> R.mipmap.japonesa
            "Mexicana" -> R.mipmap.mexicano
            "Churrasco" -> R.mipmap.churrasco
            "Hamburguer" -> R.mipmap.hamburguer
            "Cerveja" -> R.mipmap.cerveja
            "Vinho" -> R.mipmap.vinho
            "Chopp" -> R.mipmap.chopp
            "Whisky" -> R.mipmap.whisky
            "Gim" -> R.mipmap.gin
            "Caipirinha" -> R.mipmap.caipirinha
            "Boteco" -> R.mipmap.boteco
            else -> R.mipmap.padrao
        }
    }

    fun getCategoryBackgroundColor(name: String): Color {
        return when (name) {
            "Rock" -> Color(0xFFFFE4C4) // Pastel orange
            "Sertanejo" -> Color(0xFFD8BFD8) // Pastel purple
            "Indie" -> Color(0xFFB0E0E6) // Pastel blue
            "Rap" -> Color(0xFF98FB98) // Pastel green
            "Funk" -> Color(0xFFFFDAB9) // Pastel peach
            "Metal" -> Color(0xFFFAFAD2) // Pastel yellow
            "Brasileira" -> Color(0xFFE6E6FA) // Pastel lavender
            "Japonesa" -> Color(0xFFF0E68C) // Pastel khaki
            "Mexicana" -> Color(0xFFFFB6C1) // Pastel pink
            "Churrasco" -> Color(0xFFB0C4DE) // Pastel light steel blue
            "Hamburguer" -> Color(0xFFFFC0CB) // Pastel pink
            "Cerveja" -> Color(0xFFFFF8DC) // Pastel cream
            "Vinho" -> Color(0xFFEED5D2) // Pastel rose
            "Chopp" -> Color(0xFFF5DEB3) // Pastel wheat
            "Whisky" -> Color(0xFFD2B48C) // Pastel tan
            "Gim" -> Color(0xFFF4A460) // Pastel sand
            "Caipirinha" -> Color(0xFFADD8E6) // Pastel light blue
            "Boteco" -> Color(0xFF87CEFA) // Pastel sky blue
            else -> Color(0xFFF5F5F5) // Default pastel gray
        }
    }


    @Composable
    fun CategoryItem(name: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            // Colored box with the image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(getCategoryBackgroundColor(name)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = getCategoryImage(name)),
                    contentDescription = name,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Name outside the box
            Text(
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
    fun TopEstablishmentsCarousel(viewModel: EstablishmentsViewModel) {
        val topEstablishments by viewModel.topEstablishments.collectAsState()
        val establishmentPhotos by viewModel.establishmentPhotos.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Top 5 Melhores Estabelecimentos",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(topEstablishments) { establishment ->
                    val photos = establishmentPhotos[establishment.id] ?: emptyList()
                    EstablishmentCarouselItem(establishment = establishment, photos = photos)
                }
            }
        }
    }

    @Composable
    fun EstablishmentCarouselItem(establishment: Establishment, photos: List<EstablishmentPhoto>) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(200.dp)
                .padding(8.dp)
        ) {
            if (photos.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(photos.first().imgUrl),
                    contentDescription = "Foto do estabelecimento ${establishment.fantasyName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Sem imagem", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = establishment.fantasyName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }


    @Composable
    fun PopularCategoryItem(name: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            // Circular box with the image
            Box(
                modifier = Modifier
                    .size(60.dp) // Size of the circular box
                    .clip(CircleShape) // Circular shape
                    .background(getCategoryBackgroundColor(name)), // Background color
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = getCategoryImage(name)),
                    contentDescription = name,
                    modifier = Modifier.size(40.dp) // Size of the image
                )
            }

            // Name below the circular box
            Text(
                text = name,
                fontSize = 12.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }


    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun BottomMenu(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFFFFF1D5))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(
                onClick = {
                    context.startActivity(Intent(context, NotificacaoActivity::class.java))
                }
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.not),
                    contentDescription = "Notificações",
                    modifier = Modifier.size(30.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFA1530A)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.mipmap.search),
                        contentDescription = "Pesquisa",
                        modifier = Modifier.size(35.dp),
                        tint = Color.White
                    )
                }
            }

            IconButton(
                onClick = {
                    context.startActivity(Intent(context, FavoritosActivity::class.java))
                }
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.fav),
                    contentDescription = "Favoritos",
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(
                onClick = {
                    context.startActivity(Intent(context, ConfiguracoesActivity::class.java))
                }
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.user),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }


