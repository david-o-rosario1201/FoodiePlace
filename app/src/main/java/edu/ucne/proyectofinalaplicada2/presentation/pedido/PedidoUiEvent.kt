package edu.ucne.proyectofinalaplicada2.presentation.pedido

import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import java.math.BigDecimal
import java.util.Date

sealed interface PedidoUiEvent {
    data class PedidoIdChanged(val pedidoId: Int): PedidoUiEvent
    data class UsuarioIdChanged(val usuarioId: Int): PedidoUiEvent
    data class FechaPedidoChanged(val fechaPedido: Date): PedidoUiEvent
    data class TotalChanged(val total: BigDecimal): PedidoUiEvent
    data class ParaLlevarChanged(val paraLlevar: Boolean): PedidoUiEvent
    data class EstadoChanged(val estado: String): PedidoUiEvent
    data class PedidoDetalleChanged(val pedidoDetalle: List<PedidoDetalleEntity>): PedidoUiEvent
    data class IsRefreshingChanged(val isRefreshing: Boolean): PedidoUiEvent
    data class SelectedPedido(val pedidoId: Int): PedidoUiEvent
    data object Save: PedidoUiEvent
    data object Delete: PedidoUiEvent
    data object Refresh: PedidoUiEvent
}