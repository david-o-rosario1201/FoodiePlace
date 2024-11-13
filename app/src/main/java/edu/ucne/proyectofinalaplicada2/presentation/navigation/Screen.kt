package edu.ucne.proyectofinalaplicada2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object HomeScreen: Screen()

    @Serializable
    data object WelcomeScreen: Screen()

    @Serializable
    data object CarritoListScreen: Screen()

    @Serializable
    data object PedidoListScreen: Screen()

    @Serializable
    data object ReviewListScreen: Screen()

    @Serializable
    data object UsuarioListScreen: Screen()

    @Serializable
    data class UsuarioRegisterScreen(val usuarioId: Int): Screen()

    @Serializable
    data object UsuarioLoginScreen: Screen()
}