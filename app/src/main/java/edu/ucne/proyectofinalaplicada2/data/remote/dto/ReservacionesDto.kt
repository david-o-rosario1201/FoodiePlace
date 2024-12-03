package edu.ucne.proyectofinalaplicada2.data.remote.dto

import kotlinx.serialization.Serializable
import java.util.Date

data class ReservacionesDto(
    val reservacionId: Int? = 0,
    val usuarioId: Int?,
    val fechaReservacion: Date,
    val numeroPersonas: Int,
    val estado: String,
    val numeroMesa: Int,
    val horaReservacion: String?
)
