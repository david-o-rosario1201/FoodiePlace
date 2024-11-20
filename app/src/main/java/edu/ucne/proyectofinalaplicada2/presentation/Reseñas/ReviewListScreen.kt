package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.PullToRefreshLazyColumn
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        onUiEvent = viewModel::onUiEvent,
        goToAddReview = goToAddReview,
        modifier = modifier,
        onClickNotifications = onClickNotifications
    )
}

@Composable
fun ReviewListBodyScreen(
    uiState: ReviewUiState,
    onUiEvent: (ReviewUiEvent) -> Unit,
    goToAddReview: () -> Unit,
    modifier: Modifier = Modifier,
    onClickNotifications: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            PullToRefreshLazyColumn(
                items = uiState.reseñas,
                content = {
                    if (uiState.reseñas.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Image(
                                painter = painterResource(R.drawable.empty_icon),
                                contentDescription = "Lista vacía"
                            )
                            Text(
                                text = "Lista vacía",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        uiState.reseñas.forEach { review ->
                            ReviewItem(
                                item = review,
                                usuario = uiState.usuario
                            )
                        }
                    }
                },
                isRefreshing = isRefreshing,
                onRefresh = { event ->
                    scope.launch {
                        isRefreshing = true
                        onUiEvent(event)
                        delay(3000L)
                        isRefreshing = false
                    }
                },
                event = ReviewUiEvent.Refresh
            )
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
        onUiEvent = {},
        goToAddReview = {},
        onClickNotifications = {}
    )
}