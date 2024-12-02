package edu.ucne.proyectofinalaplicada2.presentation.usuario

import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity

data class UsuarioUiState(
    val usuarioId: Int? = null,
    val rol: String? = "Client",
    val nombre: String? = "",
    val telefono: String? = "",
    val correo: String? = "",
    val contrasena: String? = "",
    val fotoPerfil: String? = "",
    val confirmarContrasena: String? = "",
    val usuarios: List<UsuarioEntity> = emptyList(),
    val errorNombre: String? = "",
    val errorTelefono: String? = "",
    val errorCorreo: String? = "",
    val errorContrasena: String? = "",
    val errorConfirmarContrasena: String? = "",
    val errorCargar: String? = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)