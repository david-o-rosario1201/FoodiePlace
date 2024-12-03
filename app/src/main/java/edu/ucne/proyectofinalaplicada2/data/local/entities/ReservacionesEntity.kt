package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date


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
    @PrimaryKey(autoGenerate = true)
    val reservacionId: Int? = 0,
    val usuarioId: Int?,
    val fechaReservacion: Date,
    val numeroPersonas: Int,
    val estado: String,
    val numeroMesa: Int,
    val horaReservacion: String?

)