package edu.ucne.proyectofinalaplicada2.presentation.pagos

sealed class PagosUIEvent {
    data class IsRefreshingChanged(val isRefreshing: Boolean): PagosUIEvent()
    object Save: PagosUIEvent()
    object Delete: PagosUIEvent()
    data object Refresh: PagosUIEvent()

}