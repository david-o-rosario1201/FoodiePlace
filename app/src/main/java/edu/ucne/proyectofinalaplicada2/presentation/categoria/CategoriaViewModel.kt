package edu.ucne.proyectofinalaplicada2.presentation.categoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CategoriaDto
import edu.ucne.proyectofinalaplicada2.data.repository.CategoriaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val categoriaRepository: CategoriaRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CategoriaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        GetCategoria()
    }

    private fun GetCategoria(){
        viewModelScope.launch {
            categoriaRepository.getCategorias().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                categorias = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessge = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onUiEvent(event: CategoriaUiEvent) {
        when (event) {
            CategoriaUiEvent.Save -> saveCategoria()
            CategoriaUiEvent.Refresh -> GetCategoria()
            CategoriaUiEvent.Delete -> deleteCategoria()
            is CategoriaUiEvent.SetNombre -> _uiState.update {
                it.copy(nombre = event.nombre, nombreError = "")
            }
            is CategoriaUiEvent.SetImagen -> _uiState.update {
                it.copy(imagen = event.imagen, imagenError = "")
            }
            is CategoriaUiEvent.SetNombreError -> _uiState.update {
                it.copy(nombreError = event.error)
            }
            is CategoriaUiEvent.SetImagenError -> _uiState.update {
                it.copy(imagenError = event.error)
            }
            is CategoriaUiEvent.IsRefreshingChanged -> _uiState.update {
                it.copy(isRefreshing = event.isRefreshing)
            }
        }
    }



    private fun saveCategoria() {
        viewModelScope.launch {
            val validationError = validateCategoria(_uiState.value)
            if (validationError != null) {

                if (_uiState.value.nombre.isBlank()) {
                    _uiState.update { it.copy(nombreError = "El nombre de la categoría no puede estar vacío.") }
                }
                if (_uiState.value.imagen == null) {
                    _uiState.update { it.copy(imagenError = "Debe seleccionar una imagen.") }
                }
                _uiState.update { it.copy(success = false) }
            }
        }
    }



    private fun deleteCategoria() {
        viewModelScope.launch {
            _uiState.value.categoriaId?.let {
                categoriaRepository.deleteCategoria(it)
                _uiState.update { it.copy(success = true, errorMessge = null) }
            }
        }
    }

    private fun validateCategoria(uiState: CategoriaUiState): String? {
        return when {
            uiState.nombre.isNullOrBlank() -> "El nombre de la categoría no puede estar vacío."
            uiState.imagen == null -> "Debe seleccionar una imagen."
            else -> null
        }
    }

    fun CategoriaUiState.toEntity() =
            CategoriaDto(
                categoriaId = categoriaId,
                nombre = nombre,
                imagen = imagen
            )

}
