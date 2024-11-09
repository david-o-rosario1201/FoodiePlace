package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Entity(tableName = "Pagos")
data class PagosEntity(
    @PrimaryKey(autoGenerate = true)
    val pagoId: Int? = 0,
    val pedidoId: Int,
    val tipoTarjeta: String,
    val numeroTarjeta: String,
    val fechaExpiracion: String,
    val cvv: String,
    val fechaPago: String,
    val monto: BigDecimal
)