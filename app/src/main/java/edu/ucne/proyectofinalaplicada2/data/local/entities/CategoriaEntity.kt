package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Categorias")
data class CategoriaEntity(
    @PrimaryKey
    val categoriaId: Int? = 0,
    val nombre: String,
    val imagen: ByteArray
)
