package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDetalleDto

@Entity(tableName = "Carrito")
data class CarritoEntity(
    @PrimaryKey
    val carritoId: Int? = null,
    val usuarioId: Int,
    val fechaCreacion: String,
    val pagado: Boolean,
    val carritoDetalle: List<CarritoDetalleDto>
)