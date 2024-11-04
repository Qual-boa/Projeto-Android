package com.example.qualaboaapp.ui.theme.establishment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R

@Composable
fun LocationSection() {
    Text(
        text = stringResource(R.string.location),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = "Rua Manoel Cordeiro, 500 - Vila Nova - 09090-090",
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Image(
        painter = painterResource(id = R.drawable.map), // Imagem do mapa no drawable
        contentDescription = stringResource(R.string.mapa),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { /* Compartilhar localização */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFDBB27)),
        shape = RoundedCornerShape(30.dp)
    ) {
        Text(text = stringResource(R.string.share_location), color = Color.White)
    }
}