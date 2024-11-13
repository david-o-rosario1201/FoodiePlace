package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.math.BigDecimal

data class PedidoDetalleDto(
    val pedidoDetalleId: Int? = null,
    val pedidoId: Int,
    val productoId: Int,
    val cantidad: Int,
    val precioUnitario: BigDecimal,
    val subTotal: BigDecimal
)
