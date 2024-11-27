package edu.ucne.proyectofinalaplicada2.presentation.carrito

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.navigation.BottomBarNavigation
import edu.ucne.proyectofinalaplicada2.presentation.pedido.TotalRow
import kotlinx.coroutines.launch
import java.math.BigDecimal

@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    CarritoBodyScreen(
        uiState = uiState,
        navController = navController,
        onCarritoEvent = { event ->
            coroutineScope.launch {
                viewModel.onUiEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CarritoBodyScreen(
    uiState: CarritoUiState,
    navController: NavHostController,
    onCarritoEvent: (CarritoUiEvent) -> Unit
) {
    var isModalVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Carrito",
                onClickMenu = {},
                onClickNotifications = {},
                notificationCount = 0
            )
        },
        bottomBar = { BottomBarNavigation(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CarritoListaProductos(uiState)

            CarritoResumen(
                uiState = uiState,
                onPagarClick = { isModalVisible = true }
            )
        }

        if (isModalVisible) {
            ModalBottomSheet(
                onDismissRequest = { isModalVisible = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                CardSelectionContent(
                    uiState = uiState,
                    onCardSelected = { tarjetaId ->
                        onCarritoEvent(CarritoUiEvent.RealizarPago(tarjetaId, uiState.id ?: 0))
                        if (uiState.success) {
                            onCarritoEvent(CarritoUiEvent.LimpiarCarrito)
                            isModalVisible = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CarritoListaProductos(uiState: CarritoUiState) {
    Column(modifier = Modifier.padding(10.dp)) {
        uiState.carritoDetalle.forEach {
            CarritoRow(carrito = it)
        }
    }
}

@Composable
fun CarritoResumen(uiState: CarritoUiState, onPagarClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            TotalRow("Tiempo:", "$${uiState.tiempo}")
            TotalRow("Subtotal:", "$${uiState.subTotal}")
            TotalRow("Propina:", "$${uiState.propina}")
            TotalRow("ITBS:", "$${uiState.impuesto}")

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Black, thickness = 1.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Text("$${uiState.total}", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onPagarClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF738AFF)),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text("Pagar $${uiState.total}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            }
        }
    }
}

@Composable
fun CardSelectionContent(uiState: CarritoUiState, onCardSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona una tarjeta", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.tarjetas.isEmpty()) {
            Text("No se encontraron tarjetas disponibles.")
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(uiState.tarjetas) { tarjeta ->
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .height(120.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7E7E7)),
                        onClick = { onCardSelected(tarjeta.tarjetaId) }
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text("****${tarjeta.numeroTarjeta?.takeLast(4)}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CarritoRow(carrito: CarritoDetalleEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = carrito.productoId.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cantidad: ${carrito.cantidad}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$${carrito.precioUnitario}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${carrito.subTotal}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarritoScreenPreview() {
    val uiState = CarritoUiState(
        carritoDetalle = listOf(
            CarritoDetalleEntity(
                carritoDetalleId = 1,
                carritoId = 1,
                productoId = 101,
                cantidad = 2,
                precioUnitario = BigDecimal("15.99"),
                impuesto = BigDecimal("2.40"),
                subTotal = BigDecimal("31.98"),
                propina = BigDecimal("3.00")
            ),
            CarritoDetalleEntity(
                carritoDetalleId = 2,
                carritoId = 1,
                productoId = 102,
                cantidad = 1,
                precioUnitario = BigDecimal("25.50"),
                impuesto = BigDecimal("3.80"),
                subTotal = BigDecimal("25.50"),
                propina = BigDecimal("2.00")
            )
        ).toMutableList()
    )

    CarritoBodyScreen(
        uiState = uiState,
        navController = NavHostController(LocalContext.current),
        onCarritoEvent = {}
    )
}