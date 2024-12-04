package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.components.DatePickerField
import edu.ucne.proyectofinalaplicada2.presentation.components.TimePickerField
import java.util.*

@Composable
fun ReservacionesScreen(
    viewModel: ReservacionesViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit,
    reservacionId: Int
) {
    viewModel.getCurrentUser()

    LaunchedEffect(reservacionId) {
        if (reservacionId != 0) {
            viewModel.onEvent(ReservacionesUiEvent.SelectedReservacion(reservacionId))
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uiState.success) {
        if (uiState.success) {
            onNavigateToList()
        }
    }

    ReservacionesBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateToList = onNavigateToList,
        reservacionId = reservacionId
    )
}


@Composable
private fun ReservacionesBodyScreen(
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit,
    onNavigateToList: () -> Unit,
    reservacionId: Int
) {
    val title = if (uiState.reservacionId == 0) "Nueva Reservación" else "Editar Reservación"
    val buttonText = if (uiState.reservacionId == 0) "Guardar" else "Actualizar"

    LaunchedEffect(key1 = uiState.reservacionId) {
        if (uiState.reservacionId != 0) {
            onEvent(ReservacionesUiEvent.SelectedReservacion(uiState.reservacionId ?: 0))
        }
    }

    LaunchedEffect(key1 = uiState.success) {
        if (uiState.success) {
            onNavigateToList()
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = title,
                onClickMenu = {},
                onClickNotifications = {},
                notificationCount = 0
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ReservacionFields(
                uiState = uiState,
                onEvent = onEvent
            )

            SaveButton(
                buttonText = buttonText,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun ReservacionFields(
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Nueva Reservación",
            color = Color(0xFFFFA500),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.usuarioRol == "Admin") {
            OutlinedTextField(
                value = uiState.numeroMesa?.toString() ?: "",
                onValueChange = {
                    val numero = it.toIntOrNull()
                    onEvent(ReservacionesUiEvent.NumeroMesaChange(numero ?: 0))
                },
                label = { Text("Número de Mesa") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.estado,
                onValueChange = { estado ->
                    onEvent(ReservacionesUiEvent.EstadoChange(estado))
                },
                label = { Text("Estado") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            TimePickers(
                uiState = uiState,
                onEvent = onEvent
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        DatePickers(
            uiState = uiState,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(8.dp))



        OutlinedTextField(
            value = uiState.numeroPersonas?.toString() ?: "",
            onValueChange = {
                val numero = it.toIntOrNull()
                if (numero != null && numero > 0) {
                    onEvent(ReservacionesUiEvent.NumeroPersonasChange(numero))
                } else {
                    onEvent(ReservacionesUiEvent.NumeroPersonasChange(0))
                }
            },
            label = { Text("Número de Personas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SaveButton(
    buttonText: String,
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit
) {
    Button(
        onClick = {
            if (validateFields(uiState)) {
                onEvent(ReservacionesUiEvent.Save)
            } else {
                Log.e("ValidationError", "Datos inválidos: Fecha o número de personas incorrectos")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
    ) {
        Text(text = buttonText)
    }
}

@Composable
private fun DatePickers(
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit
) {
    DatePickerField(
        onEvent = onEvent,
        selectedDate = uiState.fechaReservacion,
        event = ReservacionesUiEvent.FechaReservacionChange(uiState.fechaReservacion ?: Date())
    )
}

@Composable
private fun TimePickers(
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit
) {
    TimePickerField(
        onEvent = onEvent,
        selectedTime = uiState.horaReservacion,
        event = ReservacionesUiEvent.HoraReservacionChange(uiState.horaReservacion)
    )
}

private fun validateFields(uiState: ReservacionesUiState): Boolean {
    if (uiState.fechaReservacion == null) {
        return false
    }
    if (uiState.numeroPersonas == null || uiState.numeroPersonas <= 0) {
        return false
    }
    return true
}
