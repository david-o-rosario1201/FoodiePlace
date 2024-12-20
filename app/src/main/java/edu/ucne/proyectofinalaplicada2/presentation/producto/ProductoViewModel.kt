package edu.ucne.proyectofinalaplicada2.presentation.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ProductoDto
import edu.ucne.proyectofinalaplicada2.data.repository.AuthRepository
import edu.ucne.proyectofinalaplicada2.data.repository.CategoriaRepository
import edu.ucne.proyectofinalaplicada2.data.repository.ProductoRepository
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject
@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val categoRepository: CategoriaRepository,
    private val usuarioRepository: UsuarioRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductoUiState())
    val productoState: StateFlow<ProductoUiState> = _uiState
    val uiState = _uiState.asStateFlow()

    init {
        getProductos()
        getCategorias()
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val currentUser = authRepository.getUser()
            val usuarioActual = usuarioRepository.getUsuarioByCorreo(currentUser ?: "")

            _uiState.update {
                it.copy(
                    usuarioNombre = usuarioActual?.nombre ?: "",
                    usuarioRol = usuarioActual?.rol,
                )
            }
        }
    }

    fun getProductoById(productoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val producto = productoRepository.getProducto(productoId)
            _uiState.value = ProductoUiState(productos = listOf(producto))
        }
    }
    fun getProductoById12(productoId: Int) {
        viewModelScope.launch {
            val producto = productoRepository.getProducto(productoId)
            _uiState.update {
                it.copy(productos = listOf(producto)) // Asegúrate de usar una lista con un solo producto
            }
        }
    }

    fun getProductoById1(productoId: Int) = flow {
        val producto = productoRepository.getProducto(productoId)
        emit(ProductoUiState(
            productoId = producto.productoId,
            nombre = producto.nombre,
            descripcion = producto.descripcion,
            precio = producto.precio,
            imagen = producto.imagen
        ))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductoUiState())

    private fun getProductos() {
        viewModelScope.launch {
            productoRepository.getProductos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                productos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                productos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategorias() {
        viewModelScope.launch {
            categoRepository.getCategorias().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(categoria = result.data ?: emptyList(), isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(categoria = emptyList(), isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun getProductosByCategoriaId(categoriaId: Int) {
        viewModelScope.launch {
            productoRepository.getProductosByCategoriaId(categoriaId).collect { productos ->
                _uiState.value = _uiState.value.copy(
                    productos = productos,
                    isLoading = false
                )
            }
        }
    }

    fun onEvent(event: ProductoUiEvent) {
        when (event) {
            is ProductoUiEvent.ProductoIdChange -> {
                _uiState.update { it.copy(productoId = event.productoId) }
            }
            is ProductoUiEvent.NombreChange -> {
                _uiState.update { it.copy(nombre = event.nombre, errorNombre = "") }
            }
            is ProductoUiEvent.CategoriaIdChange -> {
                _uiState.update { it.copy(categoriaId = event.categoriaId) }
            }
            is ProductoUiEvent.DescripcionChange -> {
                _uiState.update { it.copy(descripcion = event.descripcion) }
            }
            is ProductoUiEvent.PrecioChange -> {
                _uiState.update { it.copy(precio = event.precio) }
            }
            is ProductoUiEvent.DisponibilidadChange -> {
                _uiState.update { it.copy(disponibilidad = event.disponibilidad) }
            }
            is ProductoUiEvent.ImagenChange -> {
                _uiState.update { it.copy(imagen = event.imagen) }
            }
            is ProductoUiEvent.TiempoChange -> {
                _uiState.update { it.copy(tiempo = event.tiempo) }
            }
            is ProductoUiEvent.IsRefreshingChanged -> {
                _uiState.update { it.copy(isRefreshing = event.isRefreshing) }
            }
            is ProductoUiEvent.RestablecerCampos -> {
                _uiState.value = ProductoUiState() // Restablece los campos a sus valores iniciales
            }
            ProductoUiEvent.Refresh -> {
                getProductos()
                getCategorias()  // Actualiza también las categorías al refrescar
            }
            is ProductoUiEvent.SelectedProducto -> {
                viewModelScope.launch {
                    if (event.productoId > 0) {
                        val producto = productoRepository.getProducto(event.productoId)
                        _uiState.update {
                            it.copy(
                                productoId = producto.productoId,
                                nombre = producto.nombre,
                                categoriaId = producto.categoriaId,
                                descripcion = producto.descripcion,
                                precio = producto.precio,
                                disponibilidad = producto.disponibilidad,
                                imagen = producto.imagen
                            )
                        }
                    }
                }
            }
            ProductoUiEvent.Save -> {
                viewModelScope.launch {
                    val nombreBuscado = _uiState.value.productos
                        .find { it.nombre.lowercase() == _uiState.value.nombre?.lowercase() }

                    if (_uiState.value.nombre?.isBlank() == true) {
                        _uiState.update {
                            it.copy(errorNombre = "El nombre no puede estar vacío")
                        }
                    } else if (nombreBuscado != null && nombreBuscado.productoId != _uiState.value.productoId) {
                        _uiState.update {
                            it.copy(errorNombre = "El nombre ya existe")
                        }
                    }

                    // Validación de la imagen
                    if (_uiState.value.imagen.isNullOrBlank()) {
                        _uiState.update { it.copy(errorImagen = "Debe seleccionar una imagen") }
                    }

                    // Si no hay errores, se guarda el producto
                    if (_uiState.value.errorNombre!!.isEmpty() && _uiState.value.errorImagen.isNullOrEmpty()) {
                        if (_uiState.value.productoId == null)
                            productoRepository.addProducto(_uiState.value.toEntity())
                        else
                            productoRepository.updateProducto(
                                _uiState.value.productoId ?: 0,
                                _uiState.value.toEntity()
                            )

                        _uiState.update { it.copy(success = true) }
                    }
                }
            }
            ProductoUiEvent.Delete -> {
                viewModelScope.launch {
                    productoRepository.deleteProducto(_uiState.value.productoId ?: 0)
                }
            }
        }
    }

    fun ProductoUiState.toEntity() = ProductoDto(
        productoId = productoId ?: 0,
        nombre = nombre ?: "",
        categoriaId = categoriaId ?: 0,
        descripcion = descripcion ?: "",
        precio = precio ?: BigDecimal.ZERO,
        disponibilidad = disponibilidad ?: false,
        imagen = imagen ?: "",
        tiempo = tiempo ?:"",

    )
}
