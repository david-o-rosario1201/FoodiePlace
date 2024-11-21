package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.math.BigDecimal

data class ProductoDto (
    val productoId: Int,
    val nombre: String,
    val categoriaId: Int,
    val descripcion: String,
    val precio: BigDecimal,
    val disponibilidad: Boolean,
    val imagen: String,
    val tiempo: String

)