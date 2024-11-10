package edu.ucne.proyectofinalaplicada2.presentation.categoria

sealed interface CategoriaUiEvent {
    data class SetNombre(val nombre: String): CategoriaUiEvent
    data class SetImagen(val imagen: String): CategoriaUiEvent
    data class IsRefreshingChanged(val isRefreshing: Boolean): CategoriaUiEvent
    object Save: CategoriaUiEvent
    object Delete: CategoriaUiEvent
    data object Refresh: CategoriaUiEvent
}