package edu.ucne.proyectofinalaplicada2.presentation.reservaciones


import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiEvent
import java.util.*

@Composable
fun ReservacionesScreenAdmin(
    viewModel: ReservacionesViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReservacionesBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateToList = onNavigateToList
    )
}

@Composable
private fun ReservacionesBodyScreen(
    uiState: ReservacionesUiState,
    onEvent: (ReservacionesUiEvent) -> Unit,
    onNavigateToList: () -> Unit
) {
    val title = if (uiState.reservacionId == 0) "Nueva Reservación" else "Editar Reservación"
    val buttonText = if (uiState.reservacionId == 0) "Guardar" else "Actualizar"

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
    var showTimePicker by remember { mutableStateOf(false) }

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

        // Campo Número de Mesa
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

        // Campo Estado
        OutlinedTextField(
            value = uiState.estado ?: "",
            onValueChange = { estado ->
                onEvent(ReservacionesUiEvent.EstadoChange(estado))
            },
            label = { Text("Estado") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Hora de Reservación
        OutlinedTextField(
            value = uiState.horaReservacion ?: "",
            onValueChange = {}, // Deja esta función vacía si es de solo lectura
            label = { Text("Hora de Reservación") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showTimePicker = true },
            readOnly = true // Campo solo lectura
        )

        if (showTimePicker) {
            TimePickerDialog(
                initialHour = uiState.horaReservacion?.split(":")?.get(0)?.toIntOrNull() ?: 12,
                initialMinute = uiState.horaReservacion?.split(":")?.get(1)?.toIntOrNull() ?: 0,
                onTimeSelected = { hour, minute ->
                    val formattedTime = String.format("%02d:%02d", hour, minute)
                    onEvent(ReservacionesUiEvent.HoraReservacionChange(formattedTime))
                    showTimePicker = false
                },
                onDismissRequest = { showTimePicker = false }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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

private fun validateFields(uiState: ReservacionesUiState): Boolean {
    if (uiState.fechaReservacion == null) {
        return false
    }
    if (uiState.numeroPersonas == null || uiState.numeroPersonas <= 0) {
        return false
    }
    return true
}

@Composable
fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val currentHour = remember { mutableStateOf(initialHour) }
    val currentMinute = remember { mutableStateOf(initialMinute) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Seleccionar Hora") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format("%02d:%02d", currentHour.value, currentMinute.value),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Selector de Hora
                    NumberPicker(
                        value = currentHour.value,
                        range = 0..23,
                        onValueChange = { currentHour.value = it }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Selector de Minuto
                    NumberPicker(
                        value = currentMinute.value,
                        range = 0..59,
                        onValueChange = { currentMinute.value = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onTimeSelected(currentHour.value, currentMinute.value) }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = { if (value < range.last) onValueChange(value + 1) }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Increase")
        }
        Text(text = value.toString(), fontSize = 20.sp)
        IconButton(onClick = { if (value > range.first) onValueChange(value - 1) }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
        }
    }
}
