package edu.ucne.proyectofinalaplicada2.presentation.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDetalleDto
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDto
import edu.ucne.proyectofinalaplicada2.data.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.math.BigDecimal

class CarritoViewModel @Inject constructor(
    private val repository: CarritoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCarritos()
    }

    private fun getCarritos() {
        viewModelScope.launch {
            repository.getCarritoss().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy( isLoading = true )
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                carritos = result.data ?: emptyList(),
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

    fun onUiEvent(event: CarritoUiEvent) {
        when (event) {
            CarritoUiEvent.Save -> saveCarrito()
            CarritoUiEvent.Delete -> deleteCarrito()
            is CarritoUiEvent.IsRefreshingChanged -> _uiState.update {
                it.copy(isRefreshing = event.isRefreshing)
            }
            CarritoUiEvent.Refresh -> getCarritos()
        }
    }

    private fun saveCarrito() {
        viewModelScope.launch {
            val carritoDto = CarritoDto(
                carritoId = _uiState.value.id ?: 0,
                usuarioId = _uiState.value.usuarioId,
                fechaCreacion = _uiState.value.fechaCreacion,
                pagado = _uiState.value.pagado,
                carritoDetalle = _uiState.value.carritoDetalle.map { it.toDto() }
            )
            repository.addCarrito(carritoDto)
            _uiState.update { it.copy(success = true) }
        }
    }

    private fun deleteCarrito() {
        viewModelScope.launch {
            _uiState.value.id?.let { carritoId ->
                repository.deleteCarrito(carritoId)
                _uiState.update { it.copy(success = true) }
            }
        }
    }


    private fun CarritoDetalleEntity.toDto() = CarritoDetalleDto(
        carritoDetalleId = carritoDetalleId,
        carritoId = carritoId,
        productoId = productoId,
        cantidad = cantidad,
        precioUnitario = precioUnitario,
        impuesto = impuesto,
        subTotal = subTotal,
        propina = propina
    )
}
