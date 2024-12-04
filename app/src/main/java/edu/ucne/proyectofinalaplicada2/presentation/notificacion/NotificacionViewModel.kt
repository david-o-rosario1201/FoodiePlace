package edu.ucne.proyectofinalaplicada2.presentation.notificacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.NotificacionDto
import edu.ucne.proyectofinalaplicada2.data.repository.AuthRepository
import edu.ucne.proyectofinalaplicada2.data.repository.NotificacionRepository
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NotificacionViewModel @Inject constructor(
    private val notificacionRepository: NotificacionRepository,
    private val authRepository: AuthRepository,
    private val usuarioRepository: UsuarioRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(NotificacionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNotificaciones()
    }

    private fun getNotificaciones() {
        viewModelScope.launch {
            notificacionRepository.getNotificaciones().collectLatest { result->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        val notificaciones = result.data?.filter { notificacion ->
                            if (_uiState.value.usuarioRol == "Admin") {
                                !notificacion.descripcion.contains("Nueva Oferta", ignoreCase = true)
                            } else {
                                true
                            }
                        } ?: emptyList()

                        _uiState.update {
                            it.copy(
                                notificaciones = notificaciones,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = result.message)
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: NotificacionUiEvent){
        when(event){
            NotificacionUiEvent.Save -> {
                viewModelScope.launch {
                    if(validarCampos()){
                        notificacionRepository.addNotificacion(_uiState.value.toDto())
                    }
                }
            }
            NotificacionUiEvent.Delete -> {
                viewModelScope.launch {
                    notificacionRepository.deleteNotificacion(_uiState.value.notificacionId ?: 0)
                }
            }
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val currentUser = authRepository.getUser()
            val usuarioActual = usuarioRepository.getUsuarioByCorreo(currentUser ?: "")

            _uiState.update {
                it.copy(
                    usuarioRol = usuarioActual?.rol,
                )
            }
        }
    }

    private fun validarCampos(): Boolean {
        var isValid = true

        if(_uiState.value.descripcion.isNullOrEmpty() || _uiState.value.usuarioId == 0)
            isValid = false

        return isValid
    }

    private fun NotificacionUiState.toDto() = NotificacionDto(
        notificacionId = notificacionId,
        usuarioId = usuarioId ?: 0,
        descripcion = descripcion ?: "",
        fecha = fecha ?: Date()
    )
}