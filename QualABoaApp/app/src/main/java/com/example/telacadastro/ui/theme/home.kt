import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telacadastro.R
import com.example.telacadastro.ui.theme.ProfileScreen

val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Barra de pesquisa e localização
            SearchAndLocationBar()

            Spacer(modifier = Modifier.height(20.dp))

            // Categorias
            CategorySection()

            Spacer(modifier = Modifier.height(20.dp))

            // Estabelecimentos mais procurados
            MostSearchedEstablishments()

            Spacer(modifier = Modifier.height(20.dp))

            // Baseado nas suas buscas
            RecommendedEstablishments()

            Spacer(modifier = Modifier.height(20.dp))

            // Comidas Populares
            PopularFoodsSection()
        }

        // Menu inferior fixo
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
fun SearchAndLocationBar() {
    Column {
        Text(
            text = "São Paulo, Vila Madalena",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(30.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Pesquisar", modifier = Modifier.weight(1f), style = TextStyle(color = Color.Gray, fontSize = 16.sp))
            Icon(painter = painterResource(id = R.mipmap.search), contentDescription = "Search Icon")
        }
    }
}

@Composable
fun CategorySection() {
    Column {
        Text(text = "Visualizar Todos",
            style = TextStyle(color = Color(0xFFA1530A),
                fontWeight = FontWeight.Bold))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem("Rock")
            CategoryItem("Vinho")
            CategoryItem("Vegano")
            CategoryItem("Boteco")
            CategoryItem("Caseiro")
            CategoryItem("Cerveja")
        }
    }
}

@Composable
fun CategoryItem(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF1D5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(id = R.mipmap.home), contentDescription = name, modifier = Modifier.size(30.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, style = TextStyle(fontFamily = PoppinsFontFamily, fontSize = 12.sp))
    }
}

@Composable
fun MostSearchedEstablishments() {
    Column {
        Text(
            text = "Estabelecimentos mais procurados",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EstablishmentCard("Beer4U", R.mipmap.fav)
            EstablishmentCard("Bar do Dudu", R.mipmap.fav)
        }
    }
}

@Composable
fun RecommendedEstablishments() {
    Column {
        Text(
            text = "Baseado nas suas buscas",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EstablishmentCard("Bar do Samuca", R.mipmap.fav)
            EstablishmentCard("Bar do Samuca", R.mipmap.fav)
        }
    }
}

@Composable
fun EstablishmentCard(name: String, iconId: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = name,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontFamily = PoppinsFontFamily, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PopularFoodsSection() {
    Column {
        Text(
            text = "Comidas Populares",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        // Add similar to Establishments
    }
}

@Composable
fun BottomMenu() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.home),
                contentDescription = "Home",
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.not),
                contentDescription = "Notificações",
                modifier = Modifier.size(30.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(70.dp)
                .background(Color(0xFFA1530A), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.mipmap.search),
                    contentDescription = "Pesquisa",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }
        }

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.fav),
                contentDescription = "Favoritos",
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.mipmap.user),
                contentDescription = "Perfil",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}