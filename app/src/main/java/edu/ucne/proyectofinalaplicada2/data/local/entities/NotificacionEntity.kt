package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Notificaciones",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["usuarioId"],
            childColumns = ["usuarioId"]
        )
    ],
    indices = [Index(value = ["usuarioId"])]
)
data class NotificacionEntity(
    @PrimaryKey
    val notificacionId: Int? = null,
    val usuarioId: Int,
    val descripcion: String,
    val fecha: Date
)
