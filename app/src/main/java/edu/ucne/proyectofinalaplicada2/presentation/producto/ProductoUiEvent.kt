package edu.ucne.proyectofinalaplicada2.presentation.producto

import java.math.BigDecimal


sealed interface ProductoUiEvent {
    data class ProductoIdChange(val productoId: Int) : ProductoUiEvent
    data class NombreChange(val nombre: String) : ProductoUiEvent
    data class CategoriaIdChange(val categoriaId: Int) : ProductoUiEvent
    data class DescripcionChange(val descripcion: String) : ProductoUiEvent
    data class PrecioChange(val precio: BigDecimal) : ProductoUiEvent
    data class DisponibilidadChange(val disponibilidad: Boolean) : ProductoUiEvent
    data class ImagenChange(val imagen: String) : ProductoUiEvent
    data class SelectedProducto(val productoId: Int) : ProductoUiEvent
    data object Save : ProductoUiEvent
    data object Delete : ProductoUiEvent
}
