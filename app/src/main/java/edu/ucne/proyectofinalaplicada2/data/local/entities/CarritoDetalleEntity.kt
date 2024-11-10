package edu.ucne.proyectofinalaplicada2.data.local.entities

import java.math.BigDecimal

data class CarritoDetalleEntity (
    val carritoDetalleId : Int,
    val carritoId : Int,
    val productoId : Int,
    val cantidad : Int,
    val precioUnitario : BigDecimal,
    val impuesto : BigDecimal,
    val subTotal : BigDecimal,
    val propina : BigDecimal
)