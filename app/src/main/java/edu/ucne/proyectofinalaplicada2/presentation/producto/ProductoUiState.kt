package edu.ucne.proyectofinalaplicada2.presentation.producto

import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import java.math.BigDecimal

data class ProductoUiState(
    val productoId: Int? = null,
    val nombre: String? = "",
    val categoriaId: Int? = null,
    val descripcion: String? = "",
    val precio: BigDecimal? = BigDecimal.ZERO,
    val disponibilidad: Boolean? = true,
    val imagen: String? = "",
    val productos: List<ProductoEntity> = emptyList(),
    val categoria: List<CategoriaEntity> = emptyList(),
    val errorNombre: String? = "",
    val errorCargar: String? = "",
    val errorImagen: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
