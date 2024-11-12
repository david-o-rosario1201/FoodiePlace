import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservacionesListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Alinea el contenido horizontalmente al centro
    ) {
        // Barra amarilla en la parte superior
        Box(
            modifier = Modifier
                .fillMaxWidth() // Ancho completo
                .height(100.dp) // Altura de la barra (ajústala según necesidad)
                .background(Color(0xFFFFA500)) // Color amarillo
        )

        // Título debajo de la barra amarilla, centrado
        Text(
            text = "Mis Reservaciones",
            color = Color(0xFFFFA500), // Color amarillo
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally) // Centra el texto
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Reservaciones
        ReservacionListItem(
            mesaNumero = "5",
            estado = "Activo",
            dia = "11/2/2024",
            hora = "14:00 PM",
            personas = "4"
        )

        Spacer(modifier = Modifier.height(8.dp))

        ReservacionListItem(
            mesaNumero = "1",
            estado = "Caducada",
            dia = "11/2/2024",
            hora = "14:00 PM",
            personas = "4"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para hacer reservación, centrado
        Button(
            onClick = { /* Acción para hacer reservación */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Hacer Reservación",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ReservacionListItem(mesaNumero: String, estado: String, dia: String, hora: String, personas: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mesa #$mesaNumero",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Estado: $estado",
                    fontSize = 16.sp,
                    color = if (estado == "Activo") Color(0xFF4CAF50) else Color(0xFFFF0000)
                )
                Text(
                    text = "Día de Reservación: $dia",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Hora de Reservación: $hora",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Personas: $personas",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            // Ícono de silla para representar la mesa
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Ícono de Mesa",
                modifier = Modifier.size(40.dp),
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReservacionesScreen() {
    ReservacionesListScreen()
}
