package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@Composable
fun ReviewCreateScreen(
    viewModel: ReviewViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit,
) {
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
        ) {
            CustomTextField(
                label = "Comentario",
                value = uiState.comentario,
                onValueChange = { onEvent(ReviewUiEvent.SetComentario(it)) },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onImeAction = {},
                error = uiState.errorMessge,
            )

            OutlinedButton(
                onClick = { onEvent(ReviewUiEvent.Save) },
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
        errorMessge = ""
    )

    ReviewCreateBodyScreen(
        uiState = previewUiState,
        onEvent = {},
        onNavigateToList = {}
    )
}