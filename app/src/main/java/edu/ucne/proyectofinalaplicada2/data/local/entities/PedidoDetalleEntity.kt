package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "PedidosDetalle")
data class PedidoDetalleEntity (
    @PrimaryKey
    val pedidoDetalleId: Int? = null,
    val pedidoId: Int,
    val productoId: Int,
    val cantidad: Int,
    val precioUnitario: BigDecimal,
    val subTotal: BigDecimal
)