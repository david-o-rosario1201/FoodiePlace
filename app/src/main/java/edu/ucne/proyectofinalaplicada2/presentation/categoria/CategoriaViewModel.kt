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
            CategoriaUiEvent.Save -> {
                if (validarCampos()) {
                    saveCategoria()
                    _uiState.update { it.copy(success = true) }
                }
            }
            CategoriaUiEvent.Refresh -> GetCategoria()
            CategoriaUiEvent.Delete -> deleteCategoria()
            is CategoriaUiEvent.SetNombre -> _uiState.update {
                it.copy(nombre = event.nombre, nombreError = "")
            }
            is CategoriaUiEvent.SetImagen -> _uiState.update {
                it.copy(imagen = event.imagen.toString(), imagenError = "")
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
            if (_uiState.value.categoriaId == null) {
                categoriaRepository.addCategoria(_uiState.value.toEntity())
            } else {
                categoriaRepository.updateCategoria(_uiState.value.categoriaId ?: 0, _uiState.value.toEntity())
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

    private fun validarCampos(): Boolean {
        var isValid = true
        _uiState.update {
            it.copy(
                nombreError = if (it.nombre.isBlank()) {
                    isValid = false
                    "El campo nombre no puede estar vacío"
                } else null,
                imagenError = if (it.imagen.isBlank()) {
                    isValid = false
                    "El campo imagen no puede estar vacío"
                } else null
            )
        }
        return isValid
    }

    fun CategoriaUiState.toEntity() =
            CategoriaDto(
                categoriaId = categoriaId,
                nombre = nombre,
                imagen = imagen
            )

}
