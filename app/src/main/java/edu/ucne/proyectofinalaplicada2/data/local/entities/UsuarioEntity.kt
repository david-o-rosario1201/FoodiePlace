package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val usuarioId: Int? = null,
    val nombre: String = "",
    val telefono: String = "",
    val correo: String = "",
    val contrasena: String = ""
)
