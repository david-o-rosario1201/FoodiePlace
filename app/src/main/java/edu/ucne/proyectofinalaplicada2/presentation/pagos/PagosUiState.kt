package edu.ucne.proyectofinalaplicada2.presentation.pagos

import edu.ucne.proyectofinalaplicada2.data.local.entities.PagosEntity

data class PagosUiState (
    val id: Int? = null,
    val usuarioId: Int = 0,
    val fechaPago: String = "",
    val monto: Double = 0.0,
    val errorMessge: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val pagos: List<PagosEntity> = emptyList(),
    val isRefreshing: Boolean =false

)