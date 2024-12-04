package edu.ucne.proyectofinalaplicada2.data.remote.dto

data class CarritoDto(
    val carritoId: Int,
    val usuarioId: Int,
    val fechaCreacion: String,
    val pagado: Boolean,
    val carritoDetalle: List<CarritoDetalleDto>
)