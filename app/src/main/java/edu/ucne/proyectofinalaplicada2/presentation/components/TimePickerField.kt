package edu.ucne.proyectofinalaplicada2.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Build
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.ucne.proyectofinalaplicada2.presentation.reservaciones.ReservacionesUiEvent
import java.util.*

@Composable
fun TimePickerField(

    onEvent: (ReservacionesUiEvent) -> Unit,
    event: ReservacionesUiEvent
) {
    val context = LocalContext.current
    var timeText by remember { mutableStateOf(TextFieldValue()) }

    val timePickerDialog = remember {
        TimePickerDialog(context, { _, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = hourOfDay
            calendar[Calendar.MINUTE] = minute
            val time = calendar.time
            timeText = TextFieldValue(time.toString())
            onEvent(event)
        }, 0, 0, true)
    }


    OutlinedTextField(
        value = timeText,
        onValueChange = { timeText = it },
        label = { Text("Hora de Reservación") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { timePickerDialog.show() }) {
                Icon(imageVector = Icons.Filled.Build, contentDescription = "Seleccionar Hora")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TimePickerFieldPreview() {
    TimePickerField(

        onEvent = {},
        event = ReservacionesUiEvent.HoraReservacionChange(Date())
    )
}