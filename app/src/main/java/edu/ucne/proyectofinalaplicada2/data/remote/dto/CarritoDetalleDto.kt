package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.math.BigDecimal

data class CarritoDetalleDto (
    val carritoDetalleId : Int? = null,
    val carritoId : Int? = null,
    val productoId : Int? = null,
    val cantidad : Int? = null,
    val precioUnitario : BigDecimal? = null,
    val impuesto : BigDecimal? = null,
    val subTotal : BigDecimal? = null,
    val propina : BigDecimal? = null,
)