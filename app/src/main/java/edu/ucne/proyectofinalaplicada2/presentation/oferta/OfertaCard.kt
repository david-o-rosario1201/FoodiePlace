package edu.ucne.proyectofinalaplicada2.presentation.oferta

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OfertaCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color(0xFFFFF3E0),
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = Color(0xFFFFE0B2),
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = "🍕",
                    fontSize = 32.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Parte derecha: Información
            Column {
                Text(
                    text = "Oferta",
                    color = Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Pizza PP",
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Antes",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "$300",
                            fontSize = 16.sp,
                            color = Color.Red,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Column {
                        Text(
                            text = "Ahora",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "$250",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
