package edu.ucne.proyectofinalaplicada2.presentation.Home

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.repository.AuthRepository
import edu.ucne.proyectofinalaplicada2.data.repository.CategoriaRepository
import edu.ucne.proyectofinalaplicada2.data.repository.ProductoRepository
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaUiState
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiState
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val categoriaRepository: CategoriaRepository,
    private val usuarioRepository: UsuarioRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        loadCategorias()
        getProductos()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        searchProductos(query)
    }

    private fun loadCategorias() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            categoriaRepository.getCategorias().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            categoriaUiState = CategoriaUiState(categorias = resource.data ?: emptyList()),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = resource.message ?: "Error al cargar categorías",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun getProductos() {
        viewModelScope.launch {
            productoRepository.getProductos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(productoUiState = ProductoUiState(productos = result.data ?: emptyList()), isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val currentUser = authRepository.getUser()
            val usuarioActual = usuarioRepository.getUsuarioByCorreo(currentUser ?: "")

            _uiState.value = _uiState.value.copy(
                usuarioNombre = usuarioActual?.nombre ?: "Usuario no encontrado"
            )
        }
    }

    private fun searchProductos(query: String) {
        viewModelScope.launch {
            val filteredProductos = productoRepository.searchProductos(query)
            _uiState.value = _uiState.value.copy(
                productoUiState = ProductoUiState(productos = filteredProductos)
            )
        }
    }
}

data class HomeUiState(
    val searchQuery: String = "",
    val categoriaUiState: CategoriaUiState = CategoriaUiState(),
    val productoUiState: ProductoUiState = ProductoUiState(),
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val usuarioNombre: String = ""
)

@Composable
fun CardItem(
    nombre: String,
    descripcion: String? = null,
    imagen: String?,
    color: Color = MaterialTheme.colorScheme.primary,
    showButton: Boolean = false,
    buttonText: String = "Add",
    onButtonClick: (() -> Unit)? = null,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            imagen?.let { imagenBase64 ->
                val imagenByteArray = Base64.decode(imagenBase64, Base64.DEFAULT)
                val imagenBitmap = BitmapFactory.decodeByteArray(imagenByteArray, 0, imagenByteArray.size)

                imagenBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 8.dp)
                    )
                }
            }

            Text(
                text = nombre,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            descripcion?.let {
                Text(
                    text = it,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (showButton) {
                Button(
                    onClick = { onButtonClick?.invoke() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color_oro,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = buttonText)
                    }
                }
            }
        }
    }
}


val categoryColors = listOf(
    Color(0xFFE57373), // Rojo
    Color(0xFF81C784), // Verde
    Color(0xFF64B5F6), // Azul
    Color(0xFFFFD54F), // Amarillo
    Color(0xFFBA68C8), // Morado
    Color(0xFFFF8A65), // Naranja
    Color(0xFFAED581), // Verde claro
    Color(0xFF7986CB), // Azul oscuro
    Color(0xFFFFB74D), // Naranja claro
    Color(0xFF4DB6AC)  // Turquesa
)

fun getCategoryColor(nombre: String): Color {
    val hash = nombre.hashCode()
    val index = (hash % categoryColors.size).absoluteValue
    return categoryColors[index]
}
