package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Carrito")
data class CarritoEntity (
    @PrimaryKey
    val carritoId: Int,
    val usuarioId: Int,
    val fechaCreacion: String,
    val pagado: Boolean,
    val carritoDetalle : List<CarritoDetalleEntity>
)