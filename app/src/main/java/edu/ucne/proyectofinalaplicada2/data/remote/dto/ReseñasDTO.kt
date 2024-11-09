package edu.ucne.proyectofinalaplicada2.data.remote.dto

data class ReseñasDTO(
    val resenaId: Int,
    val usuarioId: Int,
    val comentario: String,
    val fechaResena: String,
    val calificacion: Int,
)