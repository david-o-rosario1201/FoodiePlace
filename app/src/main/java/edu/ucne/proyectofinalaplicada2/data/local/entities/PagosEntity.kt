package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Entity(tableName = "Pagos")
data class PagosEntity(
    @PrimaryKey(autoGenerate = true)
    val pagoId: Int? = null,
    val pedidoId: Int,
    val tarjetaId : Int,
    val fechaPago: String,
    val monto: BigDecimal?
)