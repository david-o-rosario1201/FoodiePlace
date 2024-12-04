package edu.ucne.proyectofinalaplicada2.presentation.carrito

import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoEntity

data class CarritoUiState (
    val id: Int? = null,
    val usuarioId: Int = 0,
    val fechaCreacion: String = "",
    val pagado: Boolean = false,
    val errorMessge: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val carritos: List<CarritoEntity> = emptyList(),
    val isRefreshing: Boolean =false,
    val carritoDetalle: List<CarritoDetalleEntity> = emptyList()

)