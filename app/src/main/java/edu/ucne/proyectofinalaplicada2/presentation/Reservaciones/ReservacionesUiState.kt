package edu.ucne.proyectofinalaplicada2.presentation.reservacion

import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity

data class ReservacionesUiState(
    val reservacionId: Int? = null,
    val usuarioId: Int? = null,
    val fechaReservacion: String? = "",
    val numeroPersonas: Int? = 0,
    val estado: String? = "",
    val reservaciones: List<ReservacionesEntity> = emptyList(),
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMensaje: String? = null
)
