package edu.ucne.proyectofinalaplicada2.presentation.carrito

import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity

sealed class CarritoUiEvent {
    object LoadCarritoDetalles : CarritoUiEvent()
    object LoadCarritos : CarritoUiEvent()
    object SaveCarrito : CarritoUiEvent()
    object DeleteCarrito : CarritoUiEvent()
    object LimpiarCarrito : CarritoUiEvent()
    data class RealizarPago(val tarjetaId: Int, val pedidoId: Int) : CarritoUiEvent()
    data class AgregarProducto(val producto: CarritoDetalleEntity, val cantidad: Int) : CarritoUiEvent()
    data class EliminarProducto(val productoId: Int) : CarritoUiEvent()
    object Refresh : CarritoUiEvent()
    data class IsRefreshingChanged(val isRefreshing: Boolean): CarritoUiEvent()

}
