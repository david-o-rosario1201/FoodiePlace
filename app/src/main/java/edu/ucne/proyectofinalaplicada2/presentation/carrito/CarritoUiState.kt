package edu.ucne.proyectofinalaplicada2.presentation.carrito

import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.TarjetaEntity
import java.math.BigDecimal

data class CarritoUiState (
    val id: Int? = null,
    val usuarioId: Int = 0,
    val fechaCreacion: String = "",
    var pagado: Boolean = false,
    val errorMessge: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val carritos: List<CarritoEntity> = emptyList(),
    val isRefreshing: Boolean =false,
    val carritoDetalle: List<CarritoDetalleEntity> = emptyList(),
    val tarjetas: List<TarjetaEntity> = emptyList(),
    val total: BigDecimal? = BigDecimal.valueOf(0.0),
    val precioUnitario : BigDecimal? = BigDecimal.ZERO,
    val impuesto : BigDecimal? = BigDecimal.ZERO,
    val subTotal : BigDecimal? = BigDecimal.ZERO,
    val propina : BigDecimal? = BigDecimal.ZERO,
    val tiempo : Int? = null

    )