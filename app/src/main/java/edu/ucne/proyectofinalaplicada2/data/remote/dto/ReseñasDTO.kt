package edu.ucne.proyectofinalaplicada2.data.remote.dto

import java.util.Date

data class ReseñasDTO (
    val resenaId: Int,
    val usuarioId: Int,
    val comentario: String,
    val fechaResena: Date,
    val calificacion: Int,
)