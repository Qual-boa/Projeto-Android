package com.example.telacadastro

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*  // Gerencia os espaçamentos e layouts
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*  // Jetpack Compose Material 3
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QualABoaScreen(
                onCadastroClick = {
                    val intent = Intent(this, CadastroActivity::class.java)
                    startActivity(intent)
                },
                onLoginClick = {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen() {
    Scaffold(
        topBar = { TopBar() },
        content = { Content() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Qual a boa de hoje?") },
        actions = {
            var searchText by remember { mutableStateOf("") }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                        .background(Color(0xFFF0F0F0), shape = CircleShape)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                )
                IconButton(onClick = { /* Ação de busca */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Buscar"
                    )
                }
            }
        }
    )
}

@Composable
fun Content() {
    val items = listOf(
        Pair(R.drawable.rock_icon, "Rock"),
        Pair(R.drawable.vinho_icon, "Vinho"),
        Pair(R.drawable.vegano_icon, "Vegano"),
        Pair(R.drawable.boteco_icon, "Boteco")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            content = {
                items(items.size) { index ->
                    GridItem(items[index].first, items[index].second)
                }
            }
        )
    }
}

@Composable
fun GridItem(imageRes: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
/*
@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Ação da Home */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = "Home"
                    )
                }
                IconButton(onClick = { /* Ação da Busca */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Buscar"
                    )
                }
                IconButton(onClick = { /* Ação do Favorito */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "Favorito"
                    )
                }
            }
        }
    )
}
*/

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SearchScreen()
}
