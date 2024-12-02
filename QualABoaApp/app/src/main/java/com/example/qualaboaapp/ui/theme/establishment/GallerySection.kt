package com.example.qualaboaapp.ui.theme.establishment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhoto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GallerySection(galleryImages: List<EstablishmentPhoto>?) {
    // Definindo o estado do pager com o número de páginas
    val pagerState = rememberPagerState(pageCount = { galleryImages?.size ?: 0})

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp)
    ) { page ->
        Image(
            painter = rememberAsyncImagePainter(galleryImages?.get(page)?.imgUrl),
            contentDescription = "Galeria Imagem $page",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
    Spacer(modifier = Modifier.height(80.dp))
}