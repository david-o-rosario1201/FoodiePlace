package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "CarritoDetalle")
data class CarritoDetalleEntity (
    @PrimaryKey
    val carritoDetalleId : Int? = null,
    val carritoId : Int? = null,
    val productoId : Int? = null,
    val cantidad : Int? = null,
    val precioUnitario : BigDecimal? = null,
    val impuesto : BigDecimal? = null,
    val subTotal : BigDecimal? = null,
    val propina : BigDecimal? = null,
)