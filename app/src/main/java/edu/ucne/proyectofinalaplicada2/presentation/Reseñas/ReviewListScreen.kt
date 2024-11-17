package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@Composable
fun ReviewListScreen(
    viewModel: ReviewViewModel = hiltViewModel(),
    goToAddReview: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNotifications: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReviewListBodyScreen(
        uiState = uiState,
        goToAddReview = goToAddReview,
        modifier = modifier,
        onClickNotifications = onClickNotifications
    )
}

@Composable
fun ReviewListBodyScreen(
    uiState: ReviewUiState,
    goToAddReview: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNotifications: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Reseñas",
                onClickMenu = {},
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToAddReview,
                containerColor = color_oro,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nuevas reseñas"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(uiState.reseñas) { review ->
                    ReviewItem(
                        item = review,
                        usuario = uiState.usuario
                    )
                }
            }
        }

    }
}

@Composable
fun ReviewItem(
    item: ReviewEntity,
    usuario: List<UsuarioEntity>
) {

    val nombreUsuario = getUsuarioName(item.usuarioId, usuario)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${nombreUsuario}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.comentario)
                StarRating(rating = item.calificacion)
            }
        }
    }
}

private fun getUsuarioName(usuatioId: Int?, usuarios:List<UsuarioEntity>): String{
    return usuarios.find { it.usuarioId == usuatioId }?.nombre ?: "Usuario desconocido"
}

@Composable
fun StarRating(rating: Int, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            val starIcon: ImageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.KeyboardArrowLeft
            Icon(
                imageVector = starIcon,
                contentDescription = null,
                tint = color_oro,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


//__________------------------------------------

// Simulación de `ReviewUiState` para el Preview
private val sampleUiState = ReviewUiState(
    reseñas = listOf(
        ReviewEntity(resenaId = 1, usuarioId = 1,
            comentario = "Esta es la primera reseña de ejemplo.", fechaResena = "2023-01-01",
            calificacion = 5),
        ReviewEntity(resenaId = 2, usuarioId = 2,
            comentario = "Reseña de ejemplo para el segundo usuario.", fechaResena = "2023-01-02",
            calificacion = 1)
    )
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewListScreenPreview() {
    ReviewListBodyScreen(
        uiState = sampleUiState,
        goToAddReview = {},
        onClickNotifications = {}
    )
}
