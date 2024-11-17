package edu.ucne.proyectofinalaplicada2.presentation.notificacion

sealed interface NotificacionUiEvent {
    data object Save : NotificacionUiEvent
    data object Delete : NotificacionUiEvent
}