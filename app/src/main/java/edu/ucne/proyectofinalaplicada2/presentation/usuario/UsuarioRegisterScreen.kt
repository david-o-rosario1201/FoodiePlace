package edu.ucne.proyectofinalaplicada2.presentation.usuario

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.presentation.components.CustomTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.SubtitleText
import edu.ucne.proyectofinalaplicada2.presentation.components.TitleText
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@Composable
fun UsuarioRegisterScreen(
    viewModel: UsuarioViewModel = hiltViewModel(),
    onLoginUsuario: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    UsuarioRegisterBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onLoginUsuario = onLoginUsuario
    )
}

@Composable
private fun UsuarioRegisterBodyScreen(
    uiState: UsuarioUiState,
    onEvent: (UsuarioUiEvent) -> Unit,
    onLoginUsuario: () -> Unit
){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            TitleText(text = "Registro de Usuarios")
            SubtitleText(text = "Por favor ingresa los datos para registrarte")

            CustomTextField(
                label = "Nombre",
                value = uiState.nombre,
                onValueChange = { onEvent(UsuarioUiEvent.NombreChanged(it)) },
                error = uiState.errorNombre,
                imeAction = ImeAction.Next,
                onImeAction = {}
            )

            CustomTextField(
                label = "Teléfono",
                value = uiState.telefono,
                onValueChange = { onEvent(UsuarioUiEvent.TelefonoChanged(it)) },
                error = uiState.errorTelefono,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone,
                onImeAction = {}
            )

            CustomTextField(
                label = "Correo",
                value = uiState.correo,
                onValueChange = { onEvent(UsuarioUiEvent.CorreoChanged(it)) },
                error = uiState.errorCorreo,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                onImeAction = {}
            )

            CustomTextField(
                label = "Contraseña",
                value = uiState.contrasena,
                onValueChange = { onEvent(UsuarioUiEvent.ContrasenaChanged(it)) },
                error = uiState.errorContrasena,
                imeAction = ImeAction.Next,
                onImeAction = {}
            )

            CustomTextField(
                label = "Confirmar contraseña",
                value = uiState.confirmarContrasena,
                onValueChange = { onEvent(UsuarioUiEvent.ConfirmarContrasenaChanged(it)) },
                error = uiState.errorConfirmarContrasena,
                imeAction = ImeAction.Done,
                onImeAction = {
                    onEvent(UsuarioUiEvent.Register)
                }
            )

            OutlinedButton(
                onClick = { onEvent(UsuarioUiEvent.Register) },
                colors = ButtonColors(
                    containerColor = color_oro,
                    contentColor = color_oro,
                    disabledContainerColor = color_oro,
                    disabledContentColor = color_oro
                ),
                shape = RoundedCornerShape(15.dp),
            ) {
                Text(
                    text = "Registrar",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Row(Modifier.padding(top = 10.dp)){
                Text("¿Ya tienes una cuenta?")
                Text(
                    text = "Login",
                    color = color_oro,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            onLoginUsuario()
                        }
                )
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UsuarioScreenPreview() {
    ProyectoFinalAplicada2Theme {
        UsuarioRegisterBodyScreen(
            uiState = UsuarioUiState(),
            onEvent = {},
            onLoginUsuario = {}
        )
    }
}