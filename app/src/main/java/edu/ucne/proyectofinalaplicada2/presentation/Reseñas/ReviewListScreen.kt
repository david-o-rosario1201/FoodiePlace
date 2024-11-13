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

@Composable
fun ReviewListScreen(
    viewModel: ReviewViewModel = hiltViewModel(),
    goToReview: (Int) -> Unit,
    goToAddReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReviewListBodyScreen(
        uiState = uiState,
        goToReview = goToReview,
        goToAddReview = goToAddReview,
        modifier = modifier
    )
}

@Composable
fun ReviewListBodyScreen(
    uiState: ReviewUiState,
    goToReview: (Int) -> Unit,
    goToAddReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Reseñas",
                onClickMenu = {},
                onClickNotifications = {},
                notificationCount = 0
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToAddReview,
                containerColor = Color(0xFFFFA500)
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
                        goToReview = goToReview,
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
    goToReview: (Int) -> Unit,
    usuario: List<UsuarioEntity>
) {

    val NombreUsuario = getUsuarioName(item.usuarioId, usuario)

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
                    text = "${NombreUsuario}",
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

private fun getUsuarioName(UsuatioId: Int?, usuarios:List<UsuarioEntity>): String{
    return usuarios.find { it.usuarioId == UsuatioId }?.nombre ?: "Usuario desconocido"
}

@Composable
fun StarRating(rating: Int, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            val starIcon: ImageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.KeyboardArrowLeft
            Icon(
                imageVector = starIcon,
                contentDescription = null,
                tint = Color(0xFFFFA500),
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
        goToReview = {},
        goToAddReview = {}
    )
}
