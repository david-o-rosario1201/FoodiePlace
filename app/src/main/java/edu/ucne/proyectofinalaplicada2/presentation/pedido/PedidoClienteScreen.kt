package edu.ucne.proyectofinalaplicada2.presentation.pedido

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import java.math.BigDecimal
import java.time.Instant
import java.util.Date

@Composable
fun PedidoClienteScreen(
    viewModel: PedidoViewModel = hiltViewModel(),
    goToPedidoList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PedidoClienteBodyScreen(
        uiState = uiState,
        goToPedidoList = goToPedidoList
    )
}

@Composable
private fun PedidoClienteBodyScreen(
    uiState: PedidoUiState,
    goToPedidoList: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = "Pedidos",
                onBackClick = goToPedidoList
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Order: ${uiState.pedidoId ?: "N/A"}",
                            color = Color.Black,
                            style = MaterialTheme.typography.displaySmall,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        uiState.pedidoDetalle.forEach { pedido ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = pedido.cantidad.toString(),
                                    color = Color.Black,
                                    style = MaterialTheme.typography.displaySmall,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start
                                )
                                Text(
                                    text = obtenerNombreProducto(pedido.productoId),
                                    color = Color.Black,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(3f),
                                    textAlign = TextAlign.Start
                                )
                                Text(
                                    text = "$${pedido.precioUnitario}",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Items Total:", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "$${uiState.total}", style = MaterialTheme.typography.bodyLarge)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Propina:", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "$10", style = MaterialTheme.typography.bodyLarge)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "ITBS:", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "$50", style = MaterialTheme.typography.bodyLarge)
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Black, thickness = 1.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total:",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$${uiState.total}",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


private val sampleProducto = listOf(
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

private val samplePedidoDetalle = listOf(
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

private val sampleUiState = PedidoUiState(
    pedidoId = 2,
    usuarioId = 1,
    fechaPedido = Date.from(Instant.now()),
    total = BigDecimal.valueOf(100.0),
    paraLlevar = true,
    estado = "Pendiente",
    pedidoDetalle = samplePedidoDetalle
)

private val productoMap = sampleProducto.associateBy { it.productoId }

private fun obtenerNombreProducto(productoId: Int): String {
    return productoMap[productoId]?.nombre ?: "Producto no encontrado"
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PedidoClienteScreenPreview(){
    ProyectoFinalAplicada2Theme {
        PedidoClienteBodyScreen(
            uiState = sampleUiState,
            goToPedidoList = {}
        )
    }
}
