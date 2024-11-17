import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaUiState
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaViewModel
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent


@Composable
fun CategoriaListScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    goToAddCategoria: () -> Unit,
    onClickNotifications: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CategoriaListBodyScreen(
        uiState = uiState,
        goToAddCategoria = goToAddCategoria,
        onClickNotifications = onClickNotifications
    )
}



@Composable
fun CategoriaListBodyScreen(
    uiState: CategoriaUiState,
    goToAddCategoria: () -> Unit,
    onClickNotifications: () -> Unit
) {
    val categorias =
        remember { mutableStateListOf<CategoriaEntity>(*uiState.categorias.toTypedArray()) }

    LaunchedEffect(uiState.categorias) {
        categorias.clear()
        categorias.addAll(uiState.categorias)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Categorías",
                onClickMenu = {},
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToAddCategoria,
                containerColor = Color(0xFFFFA500)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nuevas categorías"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            when {
                uiState.isLoading -> {
                    // Indicador de carga
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Cargando categorías...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                uiState.categorias.isEmpty() -> {
                    // Mensaje de lista vacía
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No hay categorías disponibles",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                else -> {
                    // Mostrar lista de categorías
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.categorias) { categoria ->
                            CategoriaItem(
                                item = categoria,
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CategoriaItem(
    item: CategoriaEntity,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFFE57373), Color(0xFFBA68C8), Color(0xFF64B5F6),
        Color(0xFF4DB6AC), Color(0xFFF06292), Color(0xFFFFB74D)
    )
    val randomColor = colors.random()

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = randomColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item.imagen?.let { imagenByteArray ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagenByteArray)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen categoría",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(8.dp),
                )
            }

            Text(
                text = item.nombre,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}
