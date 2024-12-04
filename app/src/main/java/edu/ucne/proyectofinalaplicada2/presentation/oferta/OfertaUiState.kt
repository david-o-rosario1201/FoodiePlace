package edu.ucne.proyectofinalaplicada2.presentation.oferta

import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import java.math.BigDecimal
import java.util.Date

data class OfertaUiState(
    val ofertasId: Int? = null,
    val productoId: Int? = 0,
    val precio: BigDecimal? = BigDecimal.valueOf(0.0),
    val descuento: BigDecimal? = BigDecimal.valueOf(0.0),
    val precioOferta: BigDecimal? = BigDecimal.valueOf(0.0),
    val fechaInicio: Date? = Date(),
    val fechaFinal: Date? = Date(),
    val imagen: Byte = 0,
    val ofertas: List<OfertaEntity> = emptyList(),
    val productos: List<ProductoEntity> = emptyList(),
    val errorProductoId: String? = "",
    val errorPrecio: String? = "",
    val errorDescuento: String? = "",
    val errorPrecioOferta: String? = "",
    val errorFechaInicio: String? = "",
    val errorFechaFinal: String? = "",
    val errorImagen: String? = "",
    val errorCargar: String? = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
