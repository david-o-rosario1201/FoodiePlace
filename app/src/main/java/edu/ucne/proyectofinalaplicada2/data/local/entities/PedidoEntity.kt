package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(
    tableName = "Pedidos",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["usuarioId"],
            childColumns = ["usuarioId"]
        )
    ],
    indices = [Index(value = ["usuarioId"])]
)
data class PedidoEntity(
    @PrimaryKey
    val pedidoId: Int? = null,
    val usuarioId: Int,
    val fechaPedido: Date,
    val total: BigDecimal,
    val paraLlevar: Boolean,
    val estado: String,
    val tiempo: String,
    val pedidoDetalle: List<PedidoDetalleEntity>
)
