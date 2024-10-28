package com.example.qualaboaapp.ui.theme.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.PoppinsFont

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
            text = stringResource(R.string.pesquisar),
            fontFamily = PoppinsFont,
            modifier = Modifier.weight(1f),
            style = TextStyle(color = Color.Gray, fontSize = 16.sp)
        )
        androidx.compose.material3.Icon(
            painter = painterResource(id = R.mipmap.search),
            contentDescription = "Search Icon"
        )
    }
}
