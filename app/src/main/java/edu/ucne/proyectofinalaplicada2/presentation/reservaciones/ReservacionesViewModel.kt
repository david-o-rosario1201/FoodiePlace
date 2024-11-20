package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReservacionesDto
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.repository.ReservacionesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReservacionesViewModel @Inject constructor(
    private val reservacionesRepository: ReservacionesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservacionesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getReservaciones()
    }

    private fun getReservaciones() {
        viewModelScope.launch {
            reservacionesRepository.getReservaciones().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                reservaciones = result.data ?: emptyList(),
                                isLoading = false,
                                errorMensaje = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                reservaciones = result.data ?: emptyList(),
                                isLoading = false,
                                errorMensaje = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: ReservacionesUiEvent) {
        when (event) {
            is ReservacionesUiEvent.ReservacionIdChange -> {
                _uiState.update { it.copy(reservacionId = event.reservacionId) }
            }
            is ReservacionesUiEvent.UsuarioIdChange -> {
                _uiState.update { it.copy(usuarioId = event.usuarioId) }
            }
            is ReservacionesUiEvent.FechaReservacionChange -> {
                _uiState.update { it.copy(fechaReservacion = event.fechaReservacion) }
            }
            is ReservacionesUiEvent.NumeroPersonasChange -> {
                _uiState.update { it.copy(numeroPersonas = event.numeroPersonas) }
            }
            is ReservacionesUiEvent.EstadoChange -> {
                _uiState.update { it.copy(estado = event.estado) }
            }
            is ReservacionesUiEvent.NumeroMesaChange -> {
                _uiState.update { it.copy(numeroMesa = event.numeroMesa) }
                }
            is ReservacionesUiEvent.HoraReservacionChange -> {
                _uiState.update { it.copy(horaReservacion = event.horaReservacion) }
            }
            is ReservacionesUiEvent.RestablecerCampos -> {
                _uiState.value = ReservacionesUiState()
            }
            is ReservacionesUiEvent.IsRefreshingChanged -> {
                _uiState.update { it.copy(isRefreshing = event.isRefreshing) }
            }
            ReservacionesUiEvent.Refresh -> {
                getReservaciones()
            }
            is ReservacionesUiEvent.SelectedReservacion -> {
                viewModelScope.launch {
                    if (event.reservacionId > 0) {
                        val reservacion = reservacionesRepository.getReservacion(event.reservacionId)
                        _uiState.update {
                            it.copy(
                                reservacionId = reservacion.reservacionId,
                                usuarioId = reservacion.usuarioId,
                                fechaReservacion = reservacion.fechaReservacion,
                                numeroPersonas = reservacion.numeroPersonas,
                                estado = reservacion.estado,
                                numeroMesa = reservacion.numeroMesa,
                                horaReservacion = reservacion.horaReservacion
                            )
                        }
                    }
                }
            }
            ReservacionesUiEvent.Save -> {
                viewModelScope.launch {
                    if (_uiState.value.reservacionId == null)
                        reservacionesRepository.addReservacion(_uiState.value.toEntity())
                    else
                        reservacionesRepository.updateReservacion(
                            _uiState.value.reservacionId ?: 0,
                            _uiState.value.toEntity()
                        )

                    _uiState.update { it.copy(success = true) }
                }
            }
            ReservacionesUiEvent.Delete -> {
                viewModelScope.launch {
                    reservacionesRepository.deleteReservacion(_uiState.value.reservacionId ?: 0)
                }
            }


        }
    }

    fun ReservacionesUiState.toEntity() = ReservacionesDto(
        reservacionId = reservacionId ?: 0,
        usuarioId = usuarioId ?: 0,
        fechaReservacion = (fechaReservacion ?: Date()),
        numeroPersonas = numeroPersonas ?: 0,
        estado = estado ?: "",
        numeroMesa = numeroMesa ?: 0,
        horaReservacion = (horaReservacion ?: Date())
    )
}
