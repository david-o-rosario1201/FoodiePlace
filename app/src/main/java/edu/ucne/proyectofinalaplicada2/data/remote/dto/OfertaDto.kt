package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.math.BigDecimal
import java.util.Date

data class OfertaDto(
    val ofertasId: Int? = null,
    val productoId: Int,
    val precio: BigDecimal,
    val descuento: BigDecimal,
    val precioOferta: BigDecimal,
    val fechaInicio: Date,
    val fechaFinal: Date,
    val imagen: Byte
)
