package edu.ucne.proyectofinalaplicada2.presentation.Reseñas

import edu.ucne.proyectofinalaplicada2.data.local.entities.ReseñasEntity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class ReseñasUiState(
    val id: Int? = null,
    val usuarioId: Int = 0,
    val comentario: String = "",
    val fechaResena: String = "",
    val calificacion: Int = 0,
    val errorMessge: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val reseñas: List<ReseñasEntity> = emptyList()
)
