package edu.ucne.proyectofinalaplicada2.data.remote.dto

data class UsuarioDto(
    val usuarioId: Int?,
    val nombre: String,
    val telefono: String,
    val correo: String,
    val contrasena: String,
    val fotoPerfil: String?
)
