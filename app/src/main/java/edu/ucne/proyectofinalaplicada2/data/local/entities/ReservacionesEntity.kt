package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(
    tableName = "Reservaciones",
            foreignKeys  = [
        androidx.room.ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["usuarioId"],
            childColumns = ["usuarioId"]
        )
    ],
    indices = [Index("usuarioId")]
)
data class ReservacionesEntity(
    @PrimaryKey
    val reservacionId: Int? = 0,
    val usuarioId: Int,
    val fechaReservacion: String,
    val numeroPersonas: Int,
    val estado: String
)