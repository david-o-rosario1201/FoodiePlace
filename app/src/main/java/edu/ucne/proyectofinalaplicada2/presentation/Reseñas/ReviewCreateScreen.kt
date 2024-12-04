package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ReviewCreateScreen(
    viewModel: ReviewViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit,
) {
    viewModel.getCurrentUser()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReviewCreateBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onUiEvent,
        onNavigateToList = onNavigateToList,
    )
}

@Composable
fun ReviewCreateBodyScreen(
    uiState: ReviewUiState,
    onEvent: (ReviewUiEvent) -> Unit,
    onNavigateToList: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = "Nueva reseña",
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
        ) {
            CustomTextField(
                opcion = OpcionTextField(
                    label = "Comentario",
                    value = uiState.comentario,
                    error = uiState.errorMessge,
                    maxLines = 5
                ),
                onValueChange = { onEvent(ReviewUiEvent.SetComentario(it)) },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                onImeAction = {}
            )


            var expanded by remember { mutableStateOf(false) }
            OutlinedButton(
                onClick = { expanded = true },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "Calificación: ${uiState.calificacion}")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                (1..5).forEach { rating ->
                    DropdownMenuItem(
                        text = { Text(text = rating.toString()) },
                        onClick = {
                            onEvent(ReviewUiEvent.SetCalificacion(rating))
                            expanded = false
                        }
                    )
                }
            }

            OutlinedButton(
                onClick = {
                    onEvent(ReviewUiEvent.Save)
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
fun ReviewCreateScreenPreview() {
    val previewUiState = ReviewUiState(
        comentario = "Esta es una reseña de ejemplo" ,
        calificacion = 4,
        errorMessge = ""
    )

    ReviewCreateBodyScreen(
        uiState = previewUiState,
        onEvent = {},
        onNavigateToList = {}
    )
}