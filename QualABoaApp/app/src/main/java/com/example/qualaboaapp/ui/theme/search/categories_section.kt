package com.example.qualaboaapp.ui.theme.search

import PoppinsFont
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentActivity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriasGrid() {
    val context = LocalContext.current // Obtenha o contexto aqui

    // Definindo as categorias e suas imagens no drawable
    val categorias = listOf(
        stringResource(R.string.rock) to R.drawable.rock_icon,
        stringResource(R.string.vinho) to R.drawable.vinho_icon,
        stringResource(R.string.sertanejo) to R.drawable.country_icon,
        stringResource(R.string.cerveja) to R.drawable.beer_icon,
        stringResource(R.string.brasileira) to R.drawable.brazil_icon,
        stringResource(R.string.fast_food) to R.drawable.fastfood_icon,
        stringResource(R.string.caseira) to R.drawable.homemade_icon,
        stringResource(R.string.ao_vivo) to R.drawable.live_icon,
        stringResource(R.string.vegano) to R.drawable.vegan_icon
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
            val backgroundColor = backgroundColors[index % backgroundColors.size]
            CategoriaCard(
                titulo = categoria.first,
                drawableId = categoria.second,
                backgroundColor = backgroundColor,
                onClick = {
                    // Intent para abrir a EstablishmentActivity
                    val intent = Intent(context, EstablishmentActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun CategoriaCard(
    titulo: String,
    drawableId: Int,
    backgroundColor: Color,
    onClick: () -> Unit // Callback para ação de clique
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() } // Adiciona ação de clique ao card
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(
                    backgroundColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = titulo,
                modifier = Modifier
                    .size(104.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFont,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
