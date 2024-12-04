package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "Tarjetas",
    foreignKeys  = [
                ForeignKey(
                    entity = UsuarioEntity::class,
                    parentColumns = ["usuarioId"],
                    childColumns = ["usuarioId"]
                )
            ],
    indices = [Index("usuarioId")]

)
data class TarjetaEntity(
    @PrimaryKey(autoGenerate = true)
    val tarjetaId: Int,
    val usuarioId: Int,
    val tipoTarjeta: String,
    val numeroTarjeta: String,
    val fechaExpiracion: String,
    val cvv: String,
)