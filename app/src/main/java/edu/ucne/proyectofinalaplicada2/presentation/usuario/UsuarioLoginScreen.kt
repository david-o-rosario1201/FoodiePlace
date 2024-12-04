package edu.ucne.proyectofinalaplicada2.presentation.usuario

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.presentation.components.CustomTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.OpcionTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.PasswordVisibilityToggle
import edu.ucne.proyectofinalaplicada2.presentation.components.SubtitleText
import edu.ucne.proyectofinalaplicada2.presentation.components.TitleText
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@Composable
fun UsuarioLoginScreen(
    viewModel: UsuarioViewModel = hiltViewModel(),
    onRegisterUsuario: () -> Unit,
    onSignClickNative: (String) -> Unit,
    onSignClickWithGoogle: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    UsuarioLoginBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onRegisterUsuario = onRegisterUsuario,
        onSignClickNative = onSignClickNative,
        onSignClickWithGoogle = onSignClickWithGoogle
    )
}

@Composable
private fun UsuarioLoginBodyScreen(
    uiState: UsuarioUiState,
    onEvent: (UsuarioUiEvent) -> Unit,
    onRegisterUsuario: () -> Unit,
    onSignClickNative: (String) -> Unit,
    onSignClickWithGoogle: () -> Unit
){
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.isSignInSuccessful) {
        if (uiState.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Inicio de sesión exitoso",
                Toast.LENGTH_LONG
            ).show()

            onSignClickNative(uiState.correo ?: "")
        }
    }

    LaunchedEffect(key1 = uiState.signInError) {
        uiState.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold { innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TitleText(text = "Login")
            SubtitleText(text = "Por favor inicia sesión para continuar")

            CustomTextField(
                opcion = OpcionTextField(
                    label = "Correo",
                    value = uiState.correo,
                    error = uiState.errorCorreo
                ),
                onValueChange = { onEvent(UsuarioUiEvent.CorreoChanged(it)) },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                onImeAction = {}
            )

            CustomTextField(
                opcion = OpcionTextField(
                    label = "Contraseña",
                    value = uiState.contrasena,
                    error = uiState.errorContrasena
                ),
                onValueChange = { onEvent(UsuarioUiEvent.ContrasenaChanged(it)) },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                onImeAction = {
                    onEvent(UsuarioUiEvent.Login)
                },
                trailingIcon = {
                    PasswordVisibilityToggle(passwordVisible = passwordVisible) {
                        passwordVisible = !passwordVisible
                    }
                },
                visualTransformation = if(passwordVisible) {
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                }
            )

            Text(
                text = "Olvidé mi contraseña",
                color = color_oro,
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .clickable {}
            )
            Button(
                onClick = { onEvent(UsuarioUiEvent.Login) },
                colors = ButtonColors(
                    containerColor = color_oro,
                    contentColor = color_oro,
                    disabledContainerColor = color_oro,
                    disabledContentColor = color_oro
                ),
                shape = RoundedCornerShape(15.dp),
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    color = Color.White

                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Divider(
                    modifier = Modifier
                        .width(200.dp)
                        .height(1.dp),
                    color = Color.Gray
                )

                Text(
                    text = "Or",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Gray)
                )

                Divider(
                    modifier = Modifier
                        .width(200.dp)
                        .height(1.dp),
                    color = Color.Gray
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onSignClickWithGoogle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google Login",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Login With Google",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Row(Modifier.padding(top = 30.dp)){
                Text("¿Aún no tienes una cuenta?")
                Text(
                    text = "Register",
                    color = color_oro,
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
            onRegisterUsuario = {},
            onSignClickNative = {},
            onSignClickWithGoogle = {}
        )
    }
}
