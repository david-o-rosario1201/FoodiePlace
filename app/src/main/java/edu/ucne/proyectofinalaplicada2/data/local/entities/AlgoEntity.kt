package edu.ucne.proyectofinalaplicada2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Algo")
data class AlgoEntity (
    @PrimaryKey
    val AlgoId: Int? = null,
    val Nombre: String = "",
    val Tipo: String = ""

)