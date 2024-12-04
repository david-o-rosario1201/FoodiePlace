package edu.ucne.proyectofinalaplicada2.data.local.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReservacionesDto(
    val reservacionId: Int? = 0,
    val usuarioId: Int,
    val fechaReservacion: String,
    val numeroPersonas: Int,
    val estado: String
)
