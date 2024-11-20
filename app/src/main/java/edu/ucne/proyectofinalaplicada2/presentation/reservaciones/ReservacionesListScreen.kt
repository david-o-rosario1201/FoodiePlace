

package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent

@Composable
fun ReservacionesListScreen(
    viewModel: ReservacionesViewModel = hiltViewModel(),
    goToReservacion: (Int) -> Unit,
    goToAddReservacion: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNotifications: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReservacionesListBodyScreen(
        uiState = uiState,
        goToReservacion = goToReservacion,
        goToAddReservacion = goToAddReservacion,
        modifier = modifier,
        onClickNotifications = onClickNotifications
    )
}
@Composable
fun ReservacionesListBodyScreen(
    uiState: ReservacionesUiState,
    goToReservacion: (Int) -> Unit,
    goToAddReservacion: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNotifications: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "",
                onClickMenu = {},
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre la barra y el título

            // Título debajo de la barra amarilla
            Text(
                text = "Mis Reservaciones",
                color = Color(0xFFFFA500), // Color amarillo
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centra el texto
            )

            Spacer(modifier = Modifier.height(16.dp))

           LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
           ) {
                items(uiState.reservaciones) { reservacion ->
                    ReservacionItem(
                        item = reservacion,
                        goToReservacion = goToReservacion
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Button(
                        onClick = { goToAddReservacion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
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
        }
    }
}


@Composable
fun ReservacionItem(
    item: ReservacionesEntity,
    goToReservacion: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Día: ${item.reservacionId}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Estado: ${item.estado}",
                    fontSize = 16.sp,
                    color = if (item.estado == "Activo") Color(0xFF4CAF50) else Color(0xFFFF0000)
                )
                Text(
                    text = "Día: ${item.fechaReservacion}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Personas: ${item.numeroPersonas}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = { goToReservacion(item.reservacionId ?: 0) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Ver detalles de reservación",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReservacionesListScreenPreview() {
    val sampleReservaciones = listOf(
        ReservacionesEntity(
            reservacionId = 1,
            usuarioId = 1,
            estado = "Activo",
            fechaReservacion = "2024-11-12",
            numeroPersonas = 4
        ),
        ReservacionesEntity(
            reservacionId = 2,
            usuarioId = 2,
            estado = "Cancelado",
            fechaReservacion = "2024-11-13",
            numeroPersonas = 2
        ),
        ReservacionesEntity(
            reservacionId = 3,
            usuarioId = 6,
            estado = "Cancelado",
            fechaReservacion = "2024-11-13",
            numeroPersonas = 2
        )
    )

    ReservacionesListBodyScreen(
        uiState = ReservacionesUiState(reservaciones = sampleReservaciones),
        goToReservacion = {},
        goToAddReservacion = {},
        onClickNotifications = {}
    )
}
