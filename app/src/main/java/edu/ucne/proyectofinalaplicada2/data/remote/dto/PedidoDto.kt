package edu.ucne.proyectofinalaplicada2.data.remote.dto

import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import java.math.BigDecimal
import java.util.Date

data class PedidoDto(
        val pedidoId: Int? = null,
        val usuarioId: Int,
        val fechaPedido: Date,
        val total: BigDecimal,
        val paraLlevar: Boolean,
        val estado: String,
        val pedidoDetalle: List<PedidoDetalleEntity>
)
