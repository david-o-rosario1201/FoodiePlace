package edu.ucne.proyectofinalaplicada2.presentation.tarjeta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import edu.ucne.proyectofinalaplicada2.presentation.components.OpcionTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@Composable
fun TarjetaScreen(
    viewModel: TarjetaViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TarjetaBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateToList = onNavigateToList,
    )
}

@Composable
fun TarjetaBodyScreen(
    uiState: TarjetaUiState,
    onEvent: (TarjetaUiEvent) -> Unit,
    onNavigateToList: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = "Nueva Tarjeta",
                onBackClick = onNavigateToList,
            )
        }
    ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = CardDefaults.cardElevation(5.dp)
            ){}
                CustomTextField(
                    opcion = OpcionTextField(
                        label = "Tipo Tarjeta",
                        value = uiState.tipoTarjeta,
                        error = uiState.errorMessge,
                        maxLines = 5
                    ),
                    onValueChange = { onEvent(TarjetaUiEvent.TipoTarjetaChanged(it)) },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    onImeAction = {}
                )
                CustomTextField(
                    opcion = OpcionTextField(
                        label = "Numero Tarjeta",
                        value = uiState.numeroTarjeta,
                        error = uiState.errorMessge,
                        maxLines = 5
                    ),
                    onValueChange = { onEvent(TarjetaUiEvent.NumeroTarjetaChanged(it)) },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    onImeAction = {}
                )
                CustomTextField(
                    opcion = OpcionTextField(
                        label = "Fecha Expiracion",
                        value = uiState.fechaExpiracion,
                        error = uiState.errorMessge,
                        maxLines = 5
                    ),
                    onValueChange = { onEvent(TarjetaUiEvent.FechaExpiracionChanged(it)) },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    onImeAction = {}
                )
                CustomTextField(
                    opcion = OpcionTextField(
                        label = "CVV",
                        value = uiState.cvv,
                        error = uiState.errorMessge,
                        maxLines = 5
                    ),
                    onValueChange = { onEvent(TarjetaUiEvent.CvvChanged(it)) },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    onImeAction = {}
                )




                OutlinedButton(
                    onClick = {
                        onEvent(TarjetaUiEvent.Save)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = color_oro,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Text(
                        text = "Guardar",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
    }



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TarjetaScreenPreview() {
    val previewUiState = TarjetaUiState(
       tipoTarjeta = "Credito",
        numeroTarjeta = "1234567890123456",
        fechaExpiracion = "12/24",
        cvv = "123",
        errorMessge = ""
    )

    TarjetaBodyScreen(
        uiState = previewUiState,
        onEvent = {},
        onNavigateToList = {}
    )
}