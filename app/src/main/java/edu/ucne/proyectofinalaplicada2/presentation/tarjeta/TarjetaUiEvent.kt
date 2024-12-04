package edu.ucne.proyectofinalaplicada2.presentation.tarjeta

sealed interface TarjetaUiEvent {
    data class UsuarioIdChanged(val usuarioId: Int): TarjetaUiEvent
    data class TipoTarjetaChanged(val tipoTarjeta: String): TarjetaUiEvent
    data class NumeroTarjetaChanged(val numeroTarjeta: String): TarjetaUiEvent
    data class FechaExpiracionChanged(val fechaExpiracion: String): TarjetaUiEvent
    data class CvvChanged(val cvv: String): TarjetaUiEvent
    data class IsRefreshingChanged(val isRefreshing: Boolean): TarjetaUiEvent
    object Save: TarjetaUiEvent
    object Delete: TarjetaUiEvent
    object Refresh: TarjetaUiEvent
    data class SelectedTarjeta(val tarjetaId: Int): TarjetaUiEvent // Evento para seleccionar una tarjeta
}
