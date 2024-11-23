

package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.PullToRefreshLazyColumn
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReservacionesListScreen(
    viewModel: ReservacionesViewModel = hiltViewModel(),
    goToReservacion: (Int) -> Unit,
    goToAddReservacion: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNotifications: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReservacionesListBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goToReservacion = goToReservacion,
        goToAddReservacion = goToAddReservacion,
        modifier = modifier,
        onClickNotifications = onClickNotifications,
        onDrawer = onDrawer
    )
}
@Composable
fun ReservacionesListBodyScreen(
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit,
    goToReservacion: (Int) -> Unit,
    goToAddReservacion: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNotifications: () -> Unit,
    onDrawer: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "",
                onClickMenu = onDrawer,
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            PullToRefreshLazyColumn(
                isRefreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        onEvent(ReservacionesUiEvent.Refresh)
                        delay(3000L)
                        isRefreshing = false
                    }
                }
            ){
                if (uiState.reservaciones.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(
                            painter = painterResource(R.drawable.empty_icon),
                            contentDescription = "Lista vacía"
                        )
                        Text(
                            text = "Lista vacía",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    uiState.reservaciones.forEach { reservacion ->
                        ReservacionItem(
                            item = reservacion,
                            goToReservacion = goToReservacion
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
        onEvent = {},
        goToReservacion = {},
        goToAddReservacion = {},
        onClickNotifications = {},
        onDrawer = {}
    )
}
