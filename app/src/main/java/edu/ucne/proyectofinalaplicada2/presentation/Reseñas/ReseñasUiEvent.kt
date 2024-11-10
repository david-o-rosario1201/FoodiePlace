package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

sealed class ReseñasUiEvent {
    data class SetUsuarioId(val usuarioId: Int): ReseñasUiEvent()
    data class SetComentario(val comentario: String): ReseñasUiEvent()
    data class SetCalificacion(val calificacion: Int): ReseñasUiEvent()
    data class IsRefreshingChanged(val isRefreshing: Boolean): ReseñasUiEvent()
    object Save: ReseñasUiEvent()
    object Delete: ReseñasUiEvent()
    data object Refresh: ReseñasUiEvent()
}