package edu.ucne.proyectofinalaplicada2.presentation.pagos

import edu.ucne.proyectofinalaplicada2.data.local.entities.PagosEntity
import java.math.BigDecimal

data class PagosUiState (
    val id: Int? = null,
    val pedidoId: Int = 0,
    val tarjetaId: Int = 0,
    val fechaPago: String = "",
    val monto: BigDecimal = BigDecimal.ZERO,
    val errorMessge: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val pagos: List<PagosEntity> = emptyList(),
    val isRefreshing: Boolean =false

)