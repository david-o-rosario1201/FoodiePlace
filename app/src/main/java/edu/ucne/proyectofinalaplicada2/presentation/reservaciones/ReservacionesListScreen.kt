package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.ListaVaciaComponent
import edu.ucne.proyectofinalaplicada2.presentation.components.PullToRefreshLazyColumn
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReservacionesListScreen(
    viewModel: ReservacionesViewModel = hiltViewModel(),
    goToReservacion: () -> Unit,
    onEdit: (Int) -> Unit,
    onClickNotifications: () -> Unit,
    onDrawer: () -> Unit
) {
    viewModel.getCurrentUser()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReservacionesListBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goToReservacion = goToReservacion,
        onEdit = onEdit,
        onClickNotifications = onClickNotifications,
        onDrawer = onDrawer
    )
}

@Composable
fun ReservacionesListBodyScreen(
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit,
    goToReservacion: () -> Unit,
    onEdit: (Int) -> Unit,
    onClickNotifications: () -> Unit,
    onDrawer: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Reservaciones",
                onClickMenu = onDrawer,
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        },
        floatingActionButton = {
            if (uiState.usuarioRol == "Admin") {
                FloatingActionButton(
                    onClick = goToReservacion,
                    containerColor = color_oro,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Agregar nueva reservación"
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
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
            ) {
                LazyColumn {
                    if (uiState.reservaciones.isEmpty()) {
                        item {
                            ListaVaciaComponent()
                        }
                    } else {
                        items(uiState.reservaciones) { reservacion ->
                            ReservacionItem(
                                item = reservacion,
                                isAdmin = uiState.usuarioRol == "Admin",
                                onEdit = onEdit
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ReservacionItem(
    item: ReservacionesEntity,
    isAdmin: Boolean,
    onEdit: (Int) -> Unit
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val formattedDate = dateFormat.format(item.fechaReservacion)
    val formattedTime = timeFormat.format(item.horaReservacion)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .let { modifier ->
                if (isAdmin) {
                    modifier.clickable { onEdit(item.reservacionId!!) }
                } else {
                    modifier
                }
            },
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
                    text = "ID: ${item.reservacionId}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Estado: ${item.estado}",
                    fontSize = 16.sp,
                    color = if (item.estado == "Activo") Color(0xFF4CAF50) else Color(0xFFFF0000)
                )
                Text(
                    text = "Fecha: $formattedDate",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Hora: $formattedTime",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Personas: ${item.numeroPersonas}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Mesa: ${item.numeroMesa}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReservacionesListScreenPreview() {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val sampleReservaciones = listOf(
        ReservacionesEntity(
            reservacionId = 1,
            usuarioId = 1,
            estado = "Activo",
            fechaReservacion = dateFormat.parse("2024-11-12") ?: Date(),
            numeroPersonas = 4,
            horaReservacion = timeFormat.parse("18:00") ?: Date() ,
            numeroMesa = 3
        ),

        ReservacionesEntity(
            reservacionId = 2,
            usuarioId = 2,
            estado = "Cancelado",
            fechaReservacion = dateFormat.parse("2024-11-13") ?: Date(),
            numeroPersonas = 2,
            horaReservacion = timeFormat.parse("19:00") ?: Date(),
            numeroMesa = 4
        ),
        ReservacionesEntity(
            reservacionId = 3,
            usuarioId = 6,
            estado = "Cancelado",
            fechaReservacion = dateFormat.parse("2024-11-13") ?: Date(),
            numeroPersonas = 2,
            horaReservacion = timeFormat.parse("20:00") ?: Date(),
            numeroMesa = 9

        )
    )

    ReservacionesListBodyScreen(
        uiState = ReservacionesUiState(reservaciones = sampleReservaciones),
        onEvent = {},
        goToReservacion = {},
        onClickNotifications = {},
        onDrawer = {},
        onEdit = {}
    )
}