package edu.ucne.proyectofinalaplicada2.presentation.carrito

import edu.ucne.proyectofinalaplicada2.presentation.Reseñas.ReviewUiEvent

interface CarritoUiEvent {
    data class IsRefreshingChanged(val isRefreshing: Boolean): CarritoUiEvent
    object Save: CarritoUiEvent
    object Delete: CarritoUiEvent
    data object Refresh: CarritoUiEvent
}