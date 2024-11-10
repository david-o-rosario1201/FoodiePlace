package edu.ucne.proyectofinalaplicada2.presentation.usuario

sealed interface UsuarioUiEvent {
    data class UsuarioIdChanged(val usuarioId: Int?): UsuarioUiEvent
    data class NombreChanged(val nombre: String): UsuarioUiEvent
    data class TelefonoChanged(val telefono: String): UsuarioUiEvent
    data class CorreoChanged(val correo: String): UsuarioUiEvent
    data class Contrasena(val contrasena: String): UsuarioUiEvent
    data class IsRefreshingChanged(val isRefreshing: Boolean): UsuarioUiEvent
    data class SelectedUsuario(val usuarioId: Int): UsuarioUiEvent
    data object Save: UsuarioUiEvent
    data object Delete: UsuarioUiEvent
    data object Refresh: UsuarioUiEvent
}