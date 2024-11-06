package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "Productos")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true)
    val productoId: Int,
    val nombre: String,
    val categoriaId: Int,
    val descripcion: String,
    val precio: BigDecimal,
    val disponibilidad: Boolean,
    val imagen: String


)