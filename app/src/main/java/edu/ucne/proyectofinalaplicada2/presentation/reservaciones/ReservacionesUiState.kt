package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import java.util.Date

data class ReservacionesUiState(
    val reservacionId: Int? = null,
    val usuarioId: Int? = null,
    val usuarioRol: String? = "",
    val fechaReservacion: Date = Date(),
    val numeroPersonas: Int = 0,
    val estado: String = "Pendiente",
    val reservaciones: List<ReservacionesEntity> = emptyList(),
    val numeroMesa: Int = 1,
    val horaReservacion: Date?= Date(),
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMensaje: String? = null,
    val isRefreshing: Boolean = false,

)
