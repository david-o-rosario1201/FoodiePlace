package edu.ucne.proyectofinalaplicada2.presentation.Home

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import edu.ucne.proyectofinalaplicada2.data.repository.CategoriaRepository
import edu.ucne.proyectofinalaplicada2.data.repository.ProductoRepository
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaUiState
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val categoriaRepository: CategoriaRepository,
    private val usuarioRepository: UsuarioRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        loadCategorias()
        loadProductos()
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


    private fun loadProductos() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            productoRepository.getProductos().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            productoUiState = ProductoUiState(productos = resource.data ?: emptyList()),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = resource.message ?: "Error al cargar productos",
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

    fun loadUsuario(usuarioId: Int) {
        viewModelScope.launch {
            val usuario = usuarioRepository.getUsuario(
                usuarioId = usuarioId
            )
            _uiState.value = _uiState.value.copy(
                usuarioNombre = usuario.nombre ?: "Usuario no encontrado"
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
fun CategoriaCard(
    nombre: String,
    imagen: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() },
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
                        contentDescription = "Imagen categoría",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 8.dp)
                    )
                }
            }

            Text(
                text = nombre,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun ProductoCard(
    nombre: String,
    precio: String,
    imagen: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() },
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
                        contentDescription = "Imagen del producto",
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

            Text(
                text = "Precio: $precio",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}