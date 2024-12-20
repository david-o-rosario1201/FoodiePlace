package edu.ucne.proyectofinalaplicada2.presentation.pagos

sealed interface PagosUIEvent {
    data class IsRefreshingChanged(val isRefreshing: Boolean): PagosUIEvent
    object Save: PagosUIEvent
    data object Refresh: PagosUIEvent
}