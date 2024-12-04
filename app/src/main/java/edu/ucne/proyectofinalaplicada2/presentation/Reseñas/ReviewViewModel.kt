package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReviewDTO
import edu.ucne.proyectofinalaplicada2.data.repository.AuthRepository
import edu.ucne.proyectofinalaplicada2.data.repository.ReviewRepository
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val authRepository: AuthRepository,
    private val usuarioRepository: UsuarioRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState = _uiState.asStateFlow()

    init {
        GetReseñas()
        getUsuarios()
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

    private fun getUsuarios(){
        viewModelScope.launch {
            usuarioRepository.getUsuarios().collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                usuarios = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                usuarios = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onUiEvent(event: ReviewUiEvent){
        when(event){
            ReviewUiEvent.Delete -> deleteReseña()
            ReviewUiEvent.Save -> saveReseña()

            is ReviewUiEvent.SetCalificacion -> _uiState.update {
                it.copy( calificacion = event.calificacion, errorMessge = "" )
            }

            is ReviewUiEvent.SetComentario -> {
                _uiState.update {
                    it.copy(comentario = event.comentario, errorMessge = "")
                }
            }
            is ReviewUiEvent.SetUsuarioId -> _uiState.update {
                it.copy( usuarioId = event.usuarioId, errorMessge = "" )
            }

            is ReviewUiEvent.IsRefreshingChanged ->  {
                _uiState.update {
                    it.copy(isRefreshing = event.isRefreshing)
                    }
                }
            ReviewUiEvent.Refresh -> GetReseñas()
        }
    }
    private fun saveReseña() {
        viewModelScope.launch {

            val errorMessage = validarReview()
            if (errorMessage != null) {
                _uiState.update { it.copy(errorMessge = errorMessage) }
                return@launch
            }

            val currentUiState = _uiState.value
            if (currentUiState.id == null) {
                currentUiState.toEntity()?.let { repository.addReview(it) }
            } else {
                currentUiState.toEntity()?.let { repository.updateReview(currentUiState.id!!, it) }
            }

            _uiState.update { ReviewUiState() }
        }
    }
    private fun validarReview(): String? {
        val currentUiState = _uiState.value

        if (currentUiState.comentario.isBlank()) {
            return "El comentario no puede estar vacío"
        }

        if (currentUiState.calificacion == 0) {
            return "Debe seleccionar una calificación"
        }

        return null
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val currentUser = authRepository.getUser()
            val usuarioActual = usuarioRepository.getUsuarioByCorreo(currentUser ?: "")

            _uiState.update {
                it.copy(
                    usuarioId = usuarioActual?.usuarioId ?: 0,
                    nombreUsuario = usuarioActual?.nombre ?: ""
                )
            }
        }
    }


    private fun deleteReseña(){
        viewModelScope.launch {
            _uiState.value.id?.let { repository.deleteReview(it) }
        }
    }

    private fun obtenerFechaActual(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun ReviewUiState.toEntity() =
            ReviewDTO(
                resenaId = id,
                usuarioId = usuarioId,
                comentario = comentario,
                fechaResena = obtenerFechaActual(),
                calificacion = calificacion
            )
}

