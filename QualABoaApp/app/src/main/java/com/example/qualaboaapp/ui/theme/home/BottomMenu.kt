package com.example.qualaboaapp.ui.theme.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qualaboaapp.R

@SuppressLint("SuspiciousIndentation")
@Composable
fun BottomMenu(navController: NavController) {
    // Estado para rastrear o índice selecionado
    var selectedIndex by remember { mutableStateOf(0) }

    val modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color(0xFFFFF1D5)) // Cor de fundo bege
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround // Espaçamento uniforme
    ) {
        // Ícones do menu
        val items = listOf(
            R.mipmap.home to "home",
            R.mipmap.search to "pesquisa",
            R.mipmap.fav to "favoritos",
            R.mipmap.user to "perfil"
        )

        items.forEachIndexed { index, item ->
            // Se o item estiver selecionado, adiciona o fundo laranja
            if (selectedIndex == index) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(colorResource(R.color.orange_qab)), // Fundo laranja
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            selectedIndex = index // Atualiza o índice selecionado
                            navController.navigate(item.second) // Navega para a rota
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = item.first),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black // Ícone preto
                        )
                    }
                }
            } else {
                // Ícones normais (sem fundo laranja)
                IconButton(
                    onClick = {
                        selectedIndex = index // Atualiza o índice selecionado
                        navController.navigate(item.second) // Navega para a rota
                    }
                ) {
                    Icon(
                        painter = painterResource(id = item.first),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Black // Ícone preto
                    )
                }
            }
        }
    }
}