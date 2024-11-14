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
                it.copy(nombre = event.nombre, errorMessge = "")
            }

            is CategoriaUiEvent.SetImagen -> _uiState.update {
                it.copy(imagen = event.imagen, errorMessge = "")
            }

            is CategoriaUiEvent.IsRefreshingChanged -> {
                _uiState.update {
                    it.copy(isRefreshing = event.isRefreshing)
                }
            }
        }
    }

    private fun saveCategoria() {
        viewModelScope.launch {
            val validationError = validateCategoria(_uiState.value)
            if (validationError != null) {
                _uiState.update {
                    it.copy(errorMessge = validationError)
                }
            } else {
                if (_uiState.value.categoriaId == null) {
                    _uiState.value.toEntity()?.let { categoriaRepository.addCategoria(it) }
                } else {
                    _uiState.value.toEntity()?.let {
                        categoriaRepository.updateCategoria(_uiState.value.categoriaId!!, it)
                    }
                }
            }
        }
    }


    private fun deleteCategoria() {
        viewModelScope.launch {
            _uiState.value.categoriaId?.let { categoriaRepository.deleteCategoria(it) }
        }
    }

    private fun validateCategoria(uiState: CategoriaUiState): String? {
        return when {
            uiState.nombre.isNullOrBlank() -> "El nombre de la categoría no puede estar vacío."
            uiState.imagen.isNullOrBlank() -> "La imagen de la categoría no puede estar vacía."
            else -> null
        }
    }

    fun CategoriaUiState.toEntity() =
        this.categoriaId?.let {
            CategoriaDto(
                categoriaId = it,
                nombre = nombre,
                imagen = imagen
            )
        }
}
