package edu.ucne.proyectofinalaplicada2.ui.theme

import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoUiState
import java.math.BigDecimal
import java.time.Instant
import java.util.Date

val sampleProducto = listOf(
    ProductoEntity(
        productoId = 1,
        nombre = "Cocacola",
        precio = BigDecimal.valueOf(20),
        descripcion = "Bebida",
        categoriaId = 1,
        disponibilidad = true,
        imagen = ""
    ),
    ProductoEntity(
        productoId = 2,
        nombre = "Pepsi",
        precio = BigDecimal.valueOf(20),
        descripcion = "Bebida",
        categoriaId = 1,
        disponibilidad = true,
        imagen = ""
    ),
)

val samplePedidoDetalle = listOf(
    PedidoDetalleEntity(
        pedidoDetalleId = 1,
        pedidoId = 1,
        productoId = 1,
        cantidad = 1,
        precioUnitario = BigDecimal.valueOf(100.0),
        subTotal = BigDecimal.valueOf(100.0)
    ),
    PedidoDetalleEntity(
        pedidoDetalleId = 2,
        pedidoId = 1,
        productoId = 2,
        cantidad = 10,
        precioUnitario = BigDecimal.valueOf(50.0),
        subTotal = BigDecimal.valueOf(50.0)
    )
)

val samplePedidoUiState = PedidoUiState(
    pedidoId = 2,
    usuarioId = 1,
    fechaPedido = Date.from(Instant.now()),
    total = BigDecimal.valueOf(100.0),
    paraLlevar = true,
    estado = "Pendiente",
    pedidoDetalle = samplePedidoDetalle
)

val productoMap = sampleProducto.associateBy { it.productoId }

fun obtenerNombreProducto(productoId: Int): String {
    return productoMap[productoId]?.nombre ?: "Producto no encontrado"
}