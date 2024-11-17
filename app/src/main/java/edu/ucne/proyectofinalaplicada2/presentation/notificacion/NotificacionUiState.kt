package edu.ucne.proyectofinalaplicada2.presentation.notificacion

import edu.ucne.proyectofinalaplicada2.data.local.entities.NotificacionEntity
import java.time.Instant
import java.util.Date

data class NotificacionUiState(
    val notificacionId: Int? = null,
    val usuarioId: Int? = 0,
    val descripcion: String? = "",
    val fecha: Date? = Date.from(Instant.now()),
    val notificaciones: List<NotificacionEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = ""
)
