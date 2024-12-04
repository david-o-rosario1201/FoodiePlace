package edu.ucne.proyectofinalaplicada2.presentation.reservaciones

import java.util.Date

sealed interface ReservacionesUiEvent {
    data class ReservacionIdChange(val reservacionId: Int?) : ReservacionesUiEvent
    data class UsuarioIdChange(val usuarioId: Int?) : ReservacionesUiEvent
    data class FechaReservacionChange(val fechaReservacion: Date) : ReservacionesUiEvent
    data class NumeroPersonasChange(val numeroPersonas: Int) : ReservacionesUiEvent
    data class EstadoChange(val estado: String) : ReservacionesUiEvent
    data object RestablecerCampos : ReservacionesUiEvent
    data class NumeroMesaChange(val numeroMesa: Int) : ReservacionesUiEvent
    data class HoraReservacionChange(val horaReservacion: Date?) : ReservacionesUiEvent

    object Save : ReservacionesUiEvent
    object Delete : ReservacionesUiEvent
    data class SelectedReservacion(val reservacionId: Int) : ReservacionesUiEvent
    data object Refresh : ReservacionesUiEvent
    data class IsRefreshingChanged(val isRefreshing: Boolean) : ReservacionesUiEvent
}
