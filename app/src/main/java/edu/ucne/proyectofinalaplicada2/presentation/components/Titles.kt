package edu.ucne.proyectofinalaplicada2.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color_oro,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        textAlign = TextAlign.Center,
        lineHeight = 50.sp,
        modifier = Modifier.padding(bottom = 20.dp)
    )
}

@Composable
fun SubtitleText(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = Color.Gray,
        modifier = Modifier
            .padding(bottom = 30.dp)
            .padding(horizontal = 40.dp)
    )
}