package edu.ucne.proyectofinalaplicada2.presentation.categoria

sealed interface CategoriaUiEvent {
    data class SetNombre(val nombre: String): CategoriaUiEvent
    data class SetNombreError(val error: String): CategoriaUiEvent // Evento para el error de nombre
    data class SetImagen(val imagen: ByteArray?): CategoriaUiEvent
    data class SetImagenError(val error: String): CategoriaUiEvent // Evento para el error de imagen
    data class IsRefreshingChanged(val isRefreshing: Boolean): CategoriaUiEvent
    object Save: CategoriaUiEvent
    object Delete: CategoriaUiEvent
    object Refresh: CategoriaUiEvent
}
