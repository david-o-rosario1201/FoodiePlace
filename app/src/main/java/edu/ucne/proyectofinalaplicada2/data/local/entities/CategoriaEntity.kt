package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
@Entity(tableName = "Categorias")
data class CategoriaEntity (
    @PrimaryKey
    val categoriaId: Int,
    val nombre: String,
    val imagen: String
)
