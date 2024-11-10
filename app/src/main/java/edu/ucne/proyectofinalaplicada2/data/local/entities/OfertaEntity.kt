package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(
    tableName = "Ofertas",
    foreignKeys = [
        ForeignKey(
            entity = ProductoEntity::class,
            parentColumns = ["productoId"],
            childColumns = ["productoId"]
        )
    ],
    indices = [Index(value = ["productoId"])]
)
data class OfertaEntity(
    @PrimaryKey
    val ofertasId: Int? = null,
    val productoId: Int,
    val precio: BigDecimal,
    val descuento: BigDecimal,
    val precioOferta: BigDecimal,
    val fechaInicio: Date,
    val fechaFinal: Date,
    val imagen: Byte
)
