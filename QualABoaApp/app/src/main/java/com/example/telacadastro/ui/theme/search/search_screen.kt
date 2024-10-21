import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.telacadastro.R
import androidx.compose.ui.res.colorResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TelaPrincipal() {
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
                text = "Qual a boa de hoje?",
                fontFamily = PoppinsFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            PesquisaBar()

            Spacer(modifier = Modifier.height(16.dp))

            // LazyVerticalGrid para renderizar as categorias
            CategoriasGrid()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(80.dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFFFFF1D5))
        ) {
            BottomMenu()
        }
    }
}

@Composable
fun PesquisaBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(30.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.Text(
            text = "Pesquisar",
            fontFamily = PoppinsFontFamily,
            modifier = Modifier.weight(1f),
            style = TextStyle(color = Color.Gray, fontSize = 16.sp)
        )
        androidx.compose.material3.Icon(
            painter = painterResource(id = R.mipmap.search),
            contentDescription = "Search Icon"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriasGrid() {
    // Definindo as categorias e suas imagens no drawable
    val categorias = listOf(
        "Rock" to R.drawable.rock_icon,
        "Vinho" to R.drawable.vinho_icon,
        "Sertanejo" to R.drawable.country_icon,
        "Cerveja" to R.drawable.beer_icon,
        "Brasileira" to R.drawable.brazil_icon,
        "Fast-Food" to R.drawable.fastfood_icon,
        "Caseira" to R.drawable.homemade_icon,
        "Ao-Vivo" to R.drawable.live_icon,
        "Vegano" to R.drawable.vegan_icon
    )

    // Lista de cores alternadas
    val backgroundColors = listOf(
        colorResource(id = R.color.yellow_qab),
        colorResource(id = R.color.wine_red_qab),
        colorResource(id = R.color.brown_qab),
        colorResource(id = R.color.blue_qab),
        colorResource(id = R.color.orange_qab)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 colunas
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        itemsIndexed(categorias) { index, categoria ->
            // Alternando a cor de fundo com base no índice
            val backgroundColor = backgroundColors[index % backgroundColors.size]
            CategoriaCard(categoria.first, categoria.second, backgroundColor)
        }
    }
}

@Composable
fun CategoriaCard(titulo: String, drawableId: Int, backgroundColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp) // Espaçamento entre os cards
    ) {
        Box(
            modifier = Modifier
                .size(160.dp) // Tamanho do card
                .background(backgroundColor, shape = RoundedCornerShape(16.dp)) // Usando cor alternada
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = titulo,
                modifier = Modifier
                    .size(104.dp) // Tamanho da imagem dentro do card
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    TelaPrincipal()
}
