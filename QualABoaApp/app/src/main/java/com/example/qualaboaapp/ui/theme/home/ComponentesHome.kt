package com.example.qualaboaapp.ui.theme

import com.example.qualaboaapp.ui.theme.search.SearchActivity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.favoritos.FavoritosActivity
import com.example.qualaboaapp.ui.theme.home.HomeActivity
import com.example.qualaboaapp.ui.theme.notificacoes.NotificacaoActivity

@Composable
fun SearchAndLocationBar() {
    var showDialog by remember { mutableStateOf(false) } // Controla a visibilidade do modal

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true }, // Mostra o modal ao clicar
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "São Paulo, Vila Madalena",
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.mipmap.location_icon),
                contentDescription = "Localização",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(30.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.pesquisar), style = TextStyle(color = Color.Gray, fontSize = 14.sp), modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.mipmap.search),
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
        }

        // Modal para seleção de localização
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = stringResource(R.string.select_location))
                },
                text = {
                    Column {
                        Text("São Paulo, Vila Madalena")
                        Text("Rio de Janeiro, Copacabana")
                        Text("Curitiba, Centro")
                        // Adicione outras opções de localização, se desejar
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Fechar")
                    }
                }
            )
        }
    }
}


@Composable
fun CategorySection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.categorias), fontSize = 16.sp, color = Color.Black)
            Text(text = stringResource(R.string.visualize_all), color = Color(0xFFA1530A), modifier = Modifier.padding(end = 8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(listOf("Rock", "Vinho", "Vegano", "Boteco", "Caseiro", "Cerveja")) { category ->
                CategoryItem(category)
            }
        }
    }
}

@Composable
fun CategoryItem(name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 8.dp)) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF1D5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.home),
                contentDescription = name,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, fontSize = 12.sp)
    }
}

@Composable
fun MostSearchedEstablishments() {
    Column {
        Text(stringResource(R.string.estabelecimentos_mais_procurados), fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                EstablishmentCarousel(name = "Beer4U", images = listOf(R.mipmap.cervejas_beer, R.mipmap.espaco_beer))
            }
            item {
                EstablishmentCarousel(name = "Bar do Dudu", images = listOf(R.mipmap.cervejas_beer, R.mipmap.espaco_beer))
            }
        }
    }
}

@Composable
fun EstablishmentCarousel(name: String, images: List<Int>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .width(220.dp)
        .padding(8.dp)) {
        LazyRow(
            modifier = Modifier
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(images) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Imagem do $name",
                    modifier = Modifier
                        .width(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RecommendedEstablishments() {
    Column {
        Text(stringResource(R.string.baseado_nas_suas_buscas), fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(3) {
                EstablishmentCard(name = "Bar do Samuca", imageId = R.mipmap.bardosamuca)
            }
        }
    }
}

@Composable
fun EstablishmentCard(name: String, imageId: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .width(140.dp)
        .padding(8.dp)) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Imagem de $name",
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontSize = 14.sp)
    }
}

@Composable
fun BottomMenu(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color(0xFFFFF1D5))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                navController.navigate("home") // Navega para HomeScreen
            }
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.home),
                contentDescription = "Home",
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = {
            navController.navigate("notificacoes") // Navega para NotificacoesScreen
        }) {
            Icon(
                painter = painterResource(id = R.mipmap.not),
                contentDescription = "Notificações",
                modifier = Modifier.size(30.dp)
            )
        }

        // Ícone central destacado
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color(0xFFA1530A)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                navController.navigate("pesquisa") // Navega para PesquisaScreen
            }) {
                Icon(
                    painter = painterResource(id = R.mipmap.search),
                    contentDescription = "Pesquisa",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }
        }

        IconButton(onClick = {
            navController.navigate("favoritos") // Navega para FavoritosScreen
        }) {
            Icon(
                painter = painterResource(id = R.mipmap.fav),
                contentDescription = "Favoritos",
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(
            onClick = {
                navController.navigate("perfil") // Navega para PerfilScreen
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


@Composable
fun PopularFoodsSection() {
    Column {
        Text(
            text = stringResource(R.string.comidas_populares),
            fontFamily = PoppinsFontHome,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

