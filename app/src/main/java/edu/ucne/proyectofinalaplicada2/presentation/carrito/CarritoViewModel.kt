package edu.ucne.proyectofinalaplicada2.presentation.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDetalleDto
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDto
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PagosDTO
import edu.ucne.proyectofinalaplicada2.data.repository.AuthRepository
import edu.ucne.proyectofinalaplicada2.data.repository.CarritoRepository
import edu.ucne.proyectofinalaplicada2.data.repository.PagosRepository
import edu.ucne.proyectofinalaplicada2.data.repository.ProductoRepository
import edu.ucne.proyectofinalaplicada2.data.repository.TarjetaRepository
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.math.BigDecimal

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val repository: CarritoRepository,
    private val pagoRepository: PagosRepository,
    private val productoRepository: ProductoRepository,
    private val tarjetaRepository: TarjetaRepository,
    private val usuarioRepository: UsuarioRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        obtenerUsuarioActual()
    }

    private fun obtenerUsuarioActual() {
        viewModelScope.launch {
            val currentUserEmail = authRepository.getUser()
            val usuario = usuarioRepository.getUsuarioByCorreo(currentUserEmail ?: "")
            if (usuario != null) {
                _uiState.update { it.copy(usuarioId = usuario.usuarioId ?: 0) }
                cargarDatosDelCarrito(usuario.usuarioId ?: 0)
            } else {
                _uiState.update { it.copy(errorMessge = "Usuario no encontrado") }
            }
        }
    }

    private fun cargarDatosDelCarrito(usuarioId: Int) {
        getCarritos(usuarioId)
        getCarritoDetalles(usuarioId)
        getTarjetas(usuarioId)
    }

    private fun getCarritos(usuarioId: Int) {
        viewModelScope.launch {
            repository.getCarritosPorUsuario(usuarioId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                carritos = result.data ?: mutableListOf(),
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

    private fun getCarritoDetalles(usuarioId: Int) {
        viewModelScope.launch {
            val carrito = repository.getLastCarritoByPersona(usuarioId)
            if (carrito != null) {
                repository.getCarritoDetallesPorCarritoId(carrito.carritoId!!).collect { detalles ->
                    _uiState.update {
                        it.copy(carritoDetalle = detalles.toMutableList())
                    }
                    calcularTotales()
                }
            } else {
                _uiState.update {
                    it.copy(errorMessge = "No se encontró ningún carrito para este usuario.")
                }
            }
        }
    }

    private fun getTarjetas(usuarioId: Int) {
        viewModelScope.launch {
            tarjetaRepository.getTarjetasPorUsuario(usuarioId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                tarjetas = result.data ?: mutableListOf(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessge = result.message ?: "Error al cargar las tarjetas.",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    private suspend fun agregarProducto(carridetalle: CarritoDetalleEntity, cantidad: Int) {
        var carritoAnterior = repository.getLastCarrito()
        if (carritoAnterior == null) {
            repository.saveCarrito(
                CarritoEntity(
                    usuarioId = _uiState.value.usuarioId,
                    fechaCreacion = System.currentTimeMillis().toString(),
                    pagado = false,
                    carritoDetalle = mutableListOf()
                )
            )
            carritoAnterior = repository.getLastCarrito()
        }
        val existeCarrito = repository.CarritoExiste(carridetalle.productoId ?: 0, carritoAnterior?.carritoId ?: 0)
        val producto  = productoRepository.getProducto(carridetalle.productoId ?:0)
        if (existeCarrito.equals(false)) {
            repository.addCarritoDetalle(
                CarritoDetalleEntity(
                    carritoId = carritoAnterior?.carritoId ?: 0,
                    productoId = carridetalle.productoId ?: 0,
                    cantidad = cantidad,
                    precioUnitario = producto.precio,
                    impuesto = BigDecimal.ZERO,
                    subTotal = cantidad.toBigDecimal() * producto.precio,
                    propina = BigDecimal.ZERO
                )
            )
        }else{
            val carriDetalleRepetido = repository.getCarritoDetalleByProductoId(carridetalle.productoId ?: 0, carritoAnterior?.carritoId ?: 0)
            val cantidad =( carriDetalleRepetido?.cantidad ?: 0) + (carridetalle.cantidad!!)
            repository.addCarritoDetalle(
                CarritoDetalleEntity(
                    carritoDetalleId = carriDetalleRepetido?.carritoDetalleId,
                    carritoId = carritoAnterior?.carritoId ?: 0,
                    productoId = carridetalle.productoId ?: 0,
                    cantidad = cantidad,
                    precioUnitario = producto.precio,
                    impuesto = BigDecimal.ZERO,
                    subTotal = cantidad.toBigDecimal() * producto.precio,
                    propina = BigDecimal.ZERO
                )
            )
        }
    }



    private fun limpiarCarrito() {
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


    suspend fun onUiEvent(event: CarritoUiEvent) {
        when (event) {
            CarritoUiEvent.SaveCarrito -> {
                saveCarrito()
            }
            CarritoUiEvent.DeleteCarrito -> {
                deleteCarrito()
            }
            is CarritoUiEvent.IsRefreshingChanged -> {
                _uiState.update {
                    it.copy(isRefreshing = event.isRefreshing)
                }
            }
            CarritoUiEvent.Refresh -> {
                getCarritos(_uiState.value.usuarioId)
            }

            is CarritoUiEvent.AgregarProducto -> agregarProducto(event.producto, event.cantidad)
            is CarritoUiEvent.EliminarProducto -> deleteCarrito()
            CarritoUiEvent.LimpiarCarrito -> limpiarCarrito()
            CarritoUiEvent.LoadCarritoDetalles -> TODO()
            CarritoUiEvent.LoadCarritos -> TODO()
            is CarritoUiEvent.RealizarPago -> {
                realizarPago(event.tarjetaId, event.pedidoId)
            }
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
                repository.addCarritoApi(carritoDto)
                _uiState.update { it.copy(success = true) }
                limpiarCarrito()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessge = "Error al guardar el carrito: ${e.localizedMessage}") }
            }
        }
    }

    private fun realizarPago(tarjetaId: Int, pedidoId: Int) {
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

                // Realiza el pago
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
        val total = _uiState.value.carritoDetalle.sumOf { it.subTotal ?: BigDecimal.ZERO }
        val impuesto = total * BigDecimal.valueOf(0.18)
        val propina = total * BigDecimal.valueOf(0.10)

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

