package edu.ucne.proyectofinalaplicada2.presentation.usuario

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.ui.theme.Menu_bar_color
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun UsuarioLoginScreen(
    viewModel: UsuarioViewModel = hiltViewModel(),
    onRegisterUsuario: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    UsuarioLoginBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onRegisterUsuario = onRegisterUsuario
    )
}

@Composable
private fun UsuarioLoginBodyScreen(
    uiState: UsuarioUiState,
    onEvent: (UsuarioUiEvent) -> Unit,
    onRegisterUsuario: () -> Unit
){
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusManager = LocalFocusManager.current

    Scaffold { innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge,
                color = Menu_bar_color,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                lineHeight = 50.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "Por favor inicia sesión para continuar",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .padding(horizontal = 40.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally,){
                TextField(
                    label = {
                        Text("Correo")
                    },
                    value = uiState.correo ?: "",
                    onValueChange = {
                        onEvent(UsuarioUiEvent.CorreoChanged(it))
                    },
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Next
                            )
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                uiState.errorCorreo?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally){
                TextField(
                    label = {
                        Text("Contraseña")
                    },
                    value = uiState.contrasena ?: "",
                    onValueChange = {
                        onEvent(UsuarioUiEvent.ContrasenaChanged(it))
                    },
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                            onEvent(UsuarioUiEvent.Login)
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                uiState.contrasena?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            Text(
                text = "Olvidé mi contraseña",
                color = Menu_bar_color,
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .clickable {}
            )
            OutlinedButton(
                onClick = { onEvent(UsuarioUiEvent.Register) },
                colors = ButtonColors(
                    containerColor = Menu_bar_color,
                    contentColor = Menu_bar_color,
                    disabledContainerColor = Menu_bar_color,
                    disabledContentColor = Menu_bar_color
                ),
                shape = RoundedCornerShape(15.dp),
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    color = Color.White

                )
            }
            Row(Modifier.padding(top = 30.dp)){
                Text("¿Ya tienes una cuenta?")
                Text(
                    text = "Login",
                    color = Menu_bar_color,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            onRegisterUsuario()
                        }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun UsuarioLoginScreenPreview() {
    ProyectoFinalAplicada2Theme {
        UsuarioLoginBodyScreen(
            uiState = UsuarioUiState(),
            onEvent = {},
            onRegisterUsuario = {}
        )
    }
}
