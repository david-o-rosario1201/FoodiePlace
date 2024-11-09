package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReseñasDTO
import edu.ucne.proyectofinalaplicada2.data.repository.ReseñasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReseñasViewModel @Inject constructor(
    private val repository: ReseñasRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ReseñasUiState())
    val uiState = _uiState.asStateFlow()

    init {
        GetReseñas()
    }

    private fun GetReseñas(){
        viewModelScope.launch {
            repository.getReseñas().collect{ result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy( isLoading = true )
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                reseñas = result.data ?: emptyList(),
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

    fun onUiEvent(event: ReseñasUiEvent){
        when(event){
            ReseñasUiEvent.Delete -> deleteReseña()
            ReseñasUiEvent.Save -> saveReseña()

            is ReseñasUiEvent.SetCalificacion -> _uiState.update {
                it.copy( calificacion = event.calificacion, errorMessge = "" )
            }

            is ReseñasUiEvent.SetComentario -> _uiState.update {
                it.copy( comentario = event.comentario, errorMessge = "" )
            }
            is ReseñasUiEvent.SetUsuarioId -> _uiState.update {
                it.copy( usuarioId = event.usuarioId, errorMessge = "" )
            }
        }
    }

    private fun saveReseña(){
        viewModelScope.launch {
            if(_uiState.value.usuarioId == null){
                _uiState.value.toDTO()?.let { repository.addReseña(it) }
            }
            else
            {
                _uiState.value.toDTO()?.let {
                    repository.updateReseña(_uiState.value.id!!, it)
                }
            }
        }
    }

    private fun deleteReseña(){
        viewModelScope.launch {
            _uiState.value.id?.let { repository.deleteReseña(it) }
        }
    }

    private fun obtenerFechaActual(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun ReseñasUiState.toDTO() = id?.let {
        ReseñasDTO(
        resenaId = it,
        usuarioId = usuarioId,
        comentario = comentario,
        fechaResena = obtenerFechaActual(),
        calificacion = calificacion
    )
    }

}