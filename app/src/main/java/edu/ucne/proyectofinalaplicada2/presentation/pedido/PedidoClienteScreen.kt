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
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.obtenerNombreProducto
import edu.ucne.proyectofinalaplicada2.ui.theme.samplePedidoUiState

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
                            PedidoRow(pedido = pedido)
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

                    TotalRow(label = "Items Total:", amount = "$${uiState.total}")
                    TotalRow(label = "Propina:", amount = "$10")
                    TotalRow(label = "ITBS:", amount = "$50")

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

@Composable
fun PedidoRow(pedido: PedidoDetalleEntity) {
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

@Composable
fun TotalRow(label: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = amount, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PedidoClienteScreenPreview(){
    ProyectoFinalAplicada2Theme {
        PedidoClienteBodyScreen(
            uiState = samplePedidoUiState,
            goToPedidoList = {}
        )
    }
}
