package edu.ucne.proyectofinalaplicada2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object UsuarioListScreen: Screen()

    @Serializable
    data class UsuarioScreen(val usuarioId: Int): Screen()
}