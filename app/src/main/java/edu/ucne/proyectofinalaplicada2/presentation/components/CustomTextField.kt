package edu.ucne.proyectofinalaplicada2.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun CustomTextField(
    opcion: OpcionTextField,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction,
    keyboardType: KeyboardType = KeyboardType.Text,
    onImeAction: () -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusManager = LocalFocusManager.current

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            label = { Text(opcion.label) },
            value = opcion.value ?: "",
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
                onDone = { onImeAction() }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            maxLines = opcion.maxLines
        )
        opcion.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

data class OpcionTextField(
    val label: String,
    val value: String?,
    val error: String?,
    val maxLines: Int = 1
)