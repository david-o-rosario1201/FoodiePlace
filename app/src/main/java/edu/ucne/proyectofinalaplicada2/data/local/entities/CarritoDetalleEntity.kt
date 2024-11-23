package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "CarritoDetalle")
data class CarritoDetalleEntity (
    @PrimaryKey
    val carritoDetalleId : Int? = null,
    val carritoId : Int,
    val productoId : Int,
    val cantidad : Int,
    val precioUnitario : BigDecimal,
    val impuesto : BigDecimal,
    val subTotal : BigDecimal,
    val propina : BigDecimal
)