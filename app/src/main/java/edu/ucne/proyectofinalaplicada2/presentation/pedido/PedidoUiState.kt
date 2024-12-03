package edu.ucne.proyectofinalaplicada2.presentation.pedido

import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoEntity
import java.math.BigDecimal
import java.util.Date

data class PedidoUiState(
    val usuarioRol: String? = "",
    val pedidoId: Int? = null,
    val usuarioId: Int? = 0,
    val fechaPedido: Date? = Date(),
    val total: BigDecimal? = BigDecimal.valueOf(0.0),
    val paraLlevar: Boolean? = false,
    val estado: String? = "",
    val pedidoDetalle: List<PedidoDetalleEntity> = emptyList(),
    val pedidos: List<PedidoEntity> = emptyList(),
    val errorUsuarioId: String? = "",
    val errorFechaPedido: String? = "",
    val errorTotal: String? = "",
    val errorParaLlevar: String? = "",
    val errorEstado: String? = "",
    val errorPedidoDetalle: String? = "",
    val errorCargar: String? = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
