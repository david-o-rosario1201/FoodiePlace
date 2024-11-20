package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import androidx.compose.foundation.background
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
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.components.DatePickerField
import java.util.Date
@Composable
fun ReservacionesScreenCliente(
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
    // Título y lógica del botón
    val title = if (uiState.reservacionId == 0) "Nueva Reservación" else "Editar Reservación"
    val buttonText = if (uiState.reservacionId == 0) "Guardar" else "Actualizar"

    LaunchedEffect(key1 = uiState.success) {
        if (uiState.success) {
            onNavigateToList() // Navegar a la lista después de guardar/actualizar
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
            // Campos de entrada
            ReservacionFields(
                uiState = uiState,
                onEvent = onEvent
            )

            // Botón para guardar
            SaveButton(
                buttonText = buttonText,
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
        // Título de la pantalla
        Text(
            text = "Nueva Reservación",
            color = Color(0xFFFFA500),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para seleccionar la fecha
        DatePickers(
            uiState = uiState,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para el número de personas
        OutlinedTextField(
            value = uiState.numeroPersonas?.toString() ?: "",
            onValueChange = {
                val numero = it.toIntOrNull()
                if (numero != null) {
                    onEvent(ReservacionesUiEvent.NumeroPersonasChange(numero))
                } else {
                    onEvent(ReservacionesUiEvent.NumeroPersonasChange(0))
                }
            },
            label = { Text("Número de Personas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SaveButton(
    buttonText: String,
    onEvent: (ReservacionesUiEvent) -> Unit
) {
    Button(
        onClick = {
            onEvent(ReservacionesUiEvent.Save)
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
        event = ReservacionesUiEvent.FechaReservacionChange((uiState.fechaReservacion ?: Date()))
    )
}
