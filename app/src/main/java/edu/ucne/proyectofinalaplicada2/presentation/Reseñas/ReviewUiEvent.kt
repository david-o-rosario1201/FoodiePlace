package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

sealed interface ReviewUiEvent {
    data class SetUsuarioId(val usuarioId: Int): ReviewUiEvent
    data class SetComentario(val comentario: String): ReviewUiEvent
    data class SetCalificacion(val calificacion: Int): ReviewUiEvent
    data class IsRefreshingChanged(val isRefreshing: Boolean): ReviewUiEvent
    object Save: ReviewUiEvent
    object Delete: ReviewUiEvent
    data object Refresh: ReviewUiEvent
}