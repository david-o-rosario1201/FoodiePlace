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
    onReservacionCreado: (ReservacionesEntity) -> Unit,
    onEvent: (ReservacionesUiEvent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Crear Reservaciones",
                onClickMenu = {},
                onClickNotifications = {},
                notificationCount = 0
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            // Contenedor de campos de entrada
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Título de la pantalla
                Text(
                    text = "Nueva Reservacion",
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
                            viewModel.onEvent(ReservacionesUiEvent.NumeroPersonasChange(numero))
                        } else {
                            // Restablecer o manejar valores no numéricos
                            viewModel.onEvent(ReservacionesUiEvent.NumeroPersonasChange(0))
                        }
                    },
                    label = { Text("Número de Personas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botón para guardar la reservación
            Button(
                onClick = {
                    viewModel.onEvent(ReservacionesUiEvent.Save)
                    if (uiState.success) {
                        val nuevaReservacion = ReservacionesEntity(
                            reservacionId = uiState.reservacionId ?: 0,
                            usuarioId = uiState.usuarioId ?: 0,
                            fechaReservacion = (uiState.fechaReservacion ?: Date()),
                            numeroPersonas = uiState.numeroPersonas ?: 0,
                            estado = uiState.estado ?: "En Espera",
                            numeroMesa = uiState.numeroMesa ?: 0,
                            horaReservacion = (uiState.horaReservacion ?: Date())
                        )
                        onReservacionCreado(nuevaReservacion)
                        // Restablecer los valores de los campos
                        viewModel.onEvent(ReservacionesUiEvent.RestablecerCampos)
                    }
                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
            ) {
                Text(text = "Guardar Reservacion")
            }
        }
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
