package edu.ucne.proyectofinalaplicada2.presentation.oferta

import java.math.BigDecimal
import java.util.Date

sealed interface OfertaUiEvent {
    data class OfertaIdChanged(val ofertaId: Int): OfertaUiEvent
    data class ProductoIdChanged(val productoId: Int): OfertaUiEvent
    data class PrecioChanged(val precio: BigDecimal): OfertaUiEvent
    data class DescuentoChanged(val descuento: BigDecimal): OfertaUiEvent
    data class PrecioOfertaChanged(val precioOferta: BigDecimal): OfertaUiEvent
    data class FechaInicioChanged(val fechaInicio: Date): OfertaUiEvent
    data class FechaFinalChanged(val fechaFinal: Date): OfertaUiEvent
    data class ImagenChanged(val imagen: Byte): OfertaUiEvent
    data class IsRefreshingChanged(val isRefreshing: Boolean): OfertaUiEvent
    data class SelectedOferta(val ofertaId: Int): OfertaUiEvent
    data object Save: OfertaUiEvent
    data object Delete: OfertaUiEvent
    data object Refresh: OfertaUiEvent
}