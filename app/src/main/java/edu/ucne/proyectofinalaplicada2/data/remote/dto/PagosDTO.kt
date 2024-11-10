package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.math.BigDecimal

data class PagosDTO (
    val pagoId: Int,
    val pedidoId: Int,
    val tarjetaId : Int,
    val fechaPago: String,
    val monto: BigDecimal
)