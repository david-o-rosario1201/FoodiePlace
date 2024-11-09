package edu.ucne.proyectofinalaplicada2.presentation.reservacion

sealed class ReservacionesUiEvent {
    data class ReservacionIdChange(val reservacionId: Int?) : ReservacionesUiEvent()
    data class UsuarioIdChange(val usuarioId: Int?) : ReservacionesUiEvent()
    data class FechaReservacionChange(val fechaReservacion: String) : ReservacionesUiEvent()
    data class NumeroPersonasChange(val numeroPersonas: Int) : ReservacionesUiEvent()
    data class EstadoChange(val estado: String) : ReservacionesUiEvent()

    object Save : ReservacionesUiEvent()
    object Delete : ReservacionesUiEvent()
    data class SelectedReservacion(val reservacionId: Int) : ReservacionesUiEvent()
}
