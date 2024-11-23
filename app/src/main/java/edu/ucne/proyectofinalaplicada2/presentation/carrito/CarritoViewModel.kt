package edu.ucne.proyectofinalaplicada2.presentation.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDetalleDto
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDto
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PagosDTO
import edu.ucne.proyectofinalaplicada2.data.repository.CarritoRepository
import edu.ucne.proyectofinalaplicada2.data.repository.PagosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.math.BigDecimal

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val repository: CarritoRepository,
    private val pagoRepository: PagosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCarritos()
        calcularTotales()
        val carritoId = _uiState.value.id ?: 0
        getCarritoDetalles(carritoId)
        //getCarrito(carritoId)
    }

    private fun getCarrito(id: Int) {
        viewModelScope.launch {
            repository.getCarritoById(id)
        }
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
                                carritos = (result.data ?: mutableListOf()).toMutableList(),
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

    fun realizarPago(tarjetaId: Int, pedidoId: Int) {
        viewModelScope.launch {
            try {
                val total = _uiState.value.total
                if (total == BigDecimal.ZERO) {
                    _uiState.update { it.copy(errorMessge = "El total no puede ser 0.") }
                    return@launch
                }

                val pagoDto = PagosDTO(
                    pagoId = 0,
                    tarjetaId = tarjetaId,
                    pedidoId = pedidoId,
                    fechaPago = System.currentTimeMillis().toString(),
                    monto = total
                )

                pagoRepository.addPago(pagoDto)

                _uiState.update {
                    it.copy(
                        success = true,
                        errorMessge = null
                    )
                }
                limpiarCarrito()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessge = "Error al realizar el pago: ${e.localizedMessage}") }
            }
        }
    }

    fun agregarProducto(productoId: Int, cantidad: Int, precioUnitario: BigDecimal) {
        val carritoId = _uiState.value.id ?: 0
        val nuevoDetalle = CarritoDetalleEntity(
            carritoDetalleId = 0,
            carritoId = carritoId,
            productoId = productoId,
            cantidad = cantidad,
            precioUnitario = precioUnitario,
            impuesto = BigDecimal.ZERO,
            subTotal = cantidad.toBigDecimal() * precioUnitario,
            propina = BigDecimal.ZERO
        )

        // Trabajar con una copia de la lista actual
        val nuevaLista = _uiState.value.carritoDetalle.toMutableList()

        // Buscar si el producto ya existe en la lista
        val detalleExistenteIndex = nuevaLista.indexOfFirst { it.productoId == productoId }

        if (detalleExistenteIndex != -1) {
            // Si existe, actualizar la cantidad y el subtotal
            val detalleExistente = nuevaLista[detalleExistenteIndex]
            nuevaLista[detalleExistenteIndex] = detalleExistente.copy(
                cantidad = detalleExistente.cantidad + cantidad,
                subTotal = (detalleExistente.cantidad + cantidad).toBigDecimal() * precioUnitario
            )
        } else {
            // Si no existe, agregar el nuevo detalle
            nuevaLista.add(nuevoDetalle)
        }

        // Actualizar el estado con la nueva lista
        _uiState.update { it.copy(carritoDetalle = nuevaLista) }

        // Guardar en la base de datos
        addCarritoDetalle(nuevoDetalle)

        // Recalcular los totales
        calcularTotales()
    }





    fun limpiarCarrito() {
        _uiState.update {
            it.copy(
                carritoDetalle = mutableListOf(),
                subTotal = BigDecimal.ZERO,
                impuesto = BigDecimal.ZERO,
                propina = BigDecimal.ZERO,
                total = BigDecimal.ZERO
            )
        }
    }

    fun addCarritoDetalle(detalle: CarritoDetalleEntity) {
        viewModelScope.launch {
            repository.addCarritoDetalle(detalle)
        }
    }

    fun getCarritoDetalles(carritoId: Int) {
        viewModelScope.launch {
            repository.getCarritoDetalles(carritoId).collect { detalles ->
                _uiState.update {
                    it.copy(carritoDetalle = detalles.toMutableList())
                }
                calcularTotales()
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
            try {
                val carritoDto = CarritoDto(
                    carritoId = _uiState.value.id ?: 0,
                    usuarioId = _uiState.value.usuarioId,
                    fechaCreacion = _uiState.value.fechaCreacion,
                    pagado = _uiState.value.pagado,
                    carritoDetalle = _uiState.value.carritoDetalle.map { it.toDto() }
                )
                repository.addCarrito(carritoDto)
                _uiState.update { it.copy(success = true) }
                limpiarCarrito()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessge = "Error al guardar el carrito: ${e.localizedMessage}") }
            }
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

    private fun calcularTotales() {
        val total = _uiState.value.carritoDetalle.sumOf { it.subTotal }
        val impuesto = total * BigDecimal.valueOf(0.18)  // Por ejemplo, un 18% de impuesto
        val propina = total * BigDecimal.valueOf(0.10)   // Por ejemplo, un 10% de propina

        // Actualizar el estado con los totales recalculados
        _uiState.update {
            it.copy(
                total = total + impuesto + propina,
                subTotal = total,
                impuesto = impuesto,
                propina = propina
            )
        }
    }
}
