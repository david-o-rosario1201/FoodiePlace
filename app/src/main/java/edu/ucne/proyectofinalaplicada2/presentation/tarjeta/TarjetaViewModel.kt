package edu.ucne.proyectofinalaplicada2.presentation.tarjeta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.TarjetaDto
import edu.ucne.proyectofinalaplicada2.data.repository.TarjetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarjetaViewModel @Inject constructor(
    private val tarjetaRepository: TarjetaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TarjetaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTarjetas()
    }

    private fun getTarjetas() {
        viewModelScope.launch {
            tarjetaRepository.getTarjetas().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        // Mapear de TarjetaEntity a TarjetaDto
                        val tarjetaDtoList = result.data?.map { entity ->
                            TarjetaDto(
                                tarjetaId = entity.tarjetaId,
                                usuarioId = entity.usuarioId,
                                tipoTarjeta = entity.tipoTarjeta,
                                numeroTarjeta = entity.numeroTarjeta,
                                fechaExpiracion = entity.fechaExpiracion,
                                cvv = entity.cvv
                            )
                        } ?: emptyList()

                        // Actualizar el estado con la lista de DTOs
                        _uiState.update {
                            it.copy(
                                tarjetas = tarjetaDtoList,
                                tarjetas = tarjetaDtoList,
                                tarjetas = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                tarjetas = result.data?.map { entity ->
                                    TarjetaDto(
                                        tarjetaId = entity.tarjetaId,
                                        usuarioId = entity.usuarioId,
                                        tipoTarjeta = entity.tipoTarjeta,
                                        numeroTarjeta = entity.numeroTarjeta,
                                        fechaExpiracion = entity.fechaExpiracion,
                                        cvv = entity.cvv
                                    )
                                } ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: TarjetaUiEvent) {
        when (event) {
            is TarjetaUiEvent.UsuarioIdChanged -> {
                _uiState.update { it.copy(usuarioId = event.usuarioId) }
            }
            is TarjetaUiEvent.TipoTarjetaChanged -> {
                _uiState.update { it.copy(tipoTarjeta = event.tipoTarjeta) }
            }
            is TarjetaUiEvent.NumeroTarjetaChanged -> {
                _uiState.update { it.copy(numeroTarjeta = event.numeroTarjeta) }
            }
            is TarjetaUiEvent.FechaExpiracionChanged -> {
                _uiState.update { it.copy(fechaExpiracion = event.fechaExpiracion) }
            }
            is TarjetaUiEvent.CvvChanged -> {
                _uiState.update { it.copy(cvv = event.cvv) }
            }
            is TarjetaUiEvent.IsRefreshingChanged -> {
                _uiState.update { it.copy(isRefreshing = event.isRefreshing) }
            }
            TarjetaUiEvent.Refresh -> {
                getTarjetas()
            }
            is TarjetaUiEvent.SelectedTarjeta -> {
                viewModelScope.launch {
                    val tarjeta = tarjetaRepository.getTarjeta(event.tarjetaId)
                    _uiState.update {
                        it.copy(
                            tarjetaId = tarjeta.tarjetaId,
                            usuarioId = tarjeta.usuarioId,
                            tipoTarjeta = tarjeta.tipoTarjeta,
                            numeroTarjeta = tarjeta.numeroTarjeta,
                            fechaExpiracion = tarjeta.fechaExpiracion,
                            cvv = tarjeta.cvv
                        )
                    }
                }
            }
            TarjetaUiEvent.Save -> {
                viewModelScope.launch {
                    if (_uiState.value.tarjetaId == null) {
                        tarjetaRepository.addTarjeta(_uiState.value.toEntity())
                    } else {
                        tarjetaRepository.updateTarjeta(
                            _uiState.value.tarjetaId ?: 0,
                            _uiState.value.toEntity()
                        )
                    }
                    _uiState.update { it.copy(success = true) }
                }
            }
            TarjetaUiEvent.Delete -> {
                viewModelScope.launch {
                    tarjetaRepository.deleteTarjeta(_uiState.value.tarjetaId ?: 0)
                }
            }
        }
    }

    private fun TarjetaUiState.toEntity() = TarjetaDto(
        tarjetaId = tarjetaId ?: 0,
        usuarioId = usuarioId ?: 0,
        tipoTarjeta = tipoTarjeta ?: "",
        numeroTarjeta = numeroTarjeta ?: "",
        fechaExpiracion = fechaExpiracion ?: "",
        cvv = cvv ?: ""
    )
}
