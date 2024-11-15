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
import edu.ucne.proyectofinalaplicada2.presentation.components.CustomDropDown
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.obtenerNombreProducto
import edu.ucne.proyectofinalaplicada2.ui.theme.samplePedidoUiState
import java.math.BigDecimal

@Composable
fun PedidoAdminScreen(
    viewModel: PedidoViewModel = hiltViewModel(),
    goToPedidoList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PedidoAdminBodyScreen(
        uiState = uiState,
        goToPedidoList = goToPedidoList
    )
}

@Composable
private fun PedidoAdminBodyScreen(
    uiState: PedidoUiState,
    goToPedidoList: () -> Unit
) {
    val estados = listOf("Pendiente", "En Proceso", "Entregado")

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
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Usando las nuevas funciones
                        PedidoRow(label = "Cliente:", value = "Juan Perez")
                        PedidoRow(label = "Orden:", value = uiState.pedidoId.toString())
                        PedidoRow(label = "Para llevar:", value = if (uiState.paraLlevar == true) "Si" else "No")
                        TotalRow(total = uiState.total ?: BigDecimal.ZERO)

                        Spacer(modifier = Modifier.height(16.dp))

                        uiState.pedidoDetalle.forEach { pedido ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = obtenerNombreProducto(pedido.productoId),
                                    color = Color.Black,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(3f),
                                    textAlign = TextAlign.Start
                                )
                                Text(
                                    text = pedido.cantidad.toString(),
                                    color = Color.Black,
                                    style = MaterialTheme.typography.displaySmall,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start
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
                    Text(
                        text = "Estado",
                        color = Color.Black,
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    CustomDropDown(
                        items = estados,
                        selectedItemId = uiState.estado,
                        onEvent = { },
                        event = Unit,
                        itemToString = { it },
                        itemId = { it }
                    )
                }
            }
        }
    }
}

@Composable
fun PedidoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun TotalRow(
    total: BigDecimal
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Total:", style = MaterialTheme.typography.bodyLarge)
        Text(text = total.toString(), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PedidoAdminScreenPreview(){
    ProyectoFinalAplicada2Theme {
        PedidoAdminBodyScreen(
            uiState = samplePedidoUiState,
            goToPedidoList = {}
        )
    }
}
