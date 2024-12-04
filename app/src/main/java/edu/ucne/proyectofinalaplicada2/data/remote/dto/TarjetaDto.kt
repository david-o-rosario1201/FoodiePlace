package edu.ucne.proyectofinalaplicada2.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TarjetaDto(
    val tarjetaId: Int? = null, // El ID podría ser opcional al crear una nueva tarjeta
    val usuarioId: Int,
    val tipoTarjeta: String,
    val numeroTarjeta: String,
    val fechaExpiracion: String,
    val cvv: String,
    val nombreTitular: String
)
