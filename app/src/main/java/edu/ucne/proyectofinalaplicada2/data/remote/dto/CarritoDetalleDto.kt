package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.math.BigDecimal

data class CarritoDetalleDto (
    val carritoDetalleId : Int? = null,
    val carritoId : Int,
    val productoId : Int,
    val cantidad : Int,
    val precioUnitario : BigDecimal,
    val impuesto : BigDecimal,
    val subTotal : BigDecimal,
    val propina : BigDecimal
)