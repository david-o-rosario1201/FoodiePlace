package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.math.BigDecimal

data class PagosDTO (
    val pagoId: Int,
    val pedidoId: Int,
    val tipoTarjeta: String,
    val numeroTarjeta: String,
    val fechaExpiracion: String,
    val cvv: String,
    val fechaPago: String,
    val monto: BigDecimal

)