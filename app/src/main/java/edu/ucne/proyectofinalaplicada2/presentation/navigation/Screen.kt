package edu.ucne.proyectofinalaplicada2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object HomeScreen: Screen()

    @Serializable
    data object ProductoScreen: Screen()

    @Serializable
    data object WelcomeScreen: Screen()

    @Serializable
    data object CarritoListScreen: Screen()

    @Serializable
    data object ProductoListScreen: Screen()

    @Serializable
    data object PedidoListScreen: Screen()

    @Serializable
    data object ReviewListScreen: Screen()

    @Serializable
    data object ReviewCreateScreen: Screen()

    @Serializable
    data object UsuarioListScreen: Screen()

    @Serializable
    data object UsuarioRegisterScreen: Screen()

    @Serializable
    data object UsuarioLoginScreen: Screen()

    @Serializable
    data object CategoriaListScreen: Screen()

    @Serializable
    data object ProductosPorCategoriaScreen: Screen()

    @Serializable
    data object CategoriaCreateScreen: Screen()

    @Serializable
    data object OfertaListScreen: Screen()

    @Serializable
    data class OfertaScreen(val ofertaId: Int): Screen()

    @Serializable
    data object PedidoAdminScreen: Screen()

    @Serializable
    data object PedidoClienteScreen: Screen()

    @Serializable
    data object AboutUsScreen: Screen()

    @Serializable
    data object ReservacionesScreenCliente: Screen()

    @Serializable
    data object NotificacionScreen: Screen()

    @Serializable
    data object ReservacionListScreen: Screen()

    @Serializable
    data object ProfileScreen: Screen()

    @Serializable
    data object ProductoAddCarritoScreen: Screen()

    @Serializable
    data object TarjetaScreen: Screen()

    }

