package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.util.Date

data class NotificacionDto(
    val notificacionId: Int? = null,
    val usuarioId: Int,
    val descripcion: String,
    val fecha: Date
)
