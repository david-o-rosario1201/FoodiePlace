package edu.ucne.proyectofinalaplicada2.presentation.carrito

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import java.math.BigDecimal

@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel = hiltViewModel(),
    navController: NavHostController,
    goToCarritoList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CarritoBodyScreen(
        uiState = uiState,
        goToPago = goToCarritoList,
        navController = navController
    )
}

@Composable
private fun CarritoBodyScreen(
    uiState: CarritoUiState,
    goToPago: () -> Unit,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Carrito",
                onClickMenu = {},
                onClickNotifications = {},
                notificationCount = 0
            )
        },
        bottomBar = {
            BottomBarNavigation(
                navController = navController
            )
        }
    ){paddingValues ->
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = androidx.compose.ui.Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = androidx.compose.ui.Modifier.padding(20.dp)
                ) {
                    uiState.carritoDetalle.forEach {
                        CarritoRow(carrito = it)
                    }
                }
            }

            Card(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = androidx.compose.ui.Modifier.padding(20.dp)
                ) {
                    TotalRow(label = "Tiempo:", amount = "$${uiState.tiempo}")
                    TotalRow(label = "Items Total:", amount = "$${uiState.total}")
                    TotalRow(label = "Propina:", amount = "$${uiState.propina}")
                    TotalRow(label = "ITBS:", amount = "$${uiState.impuesto}")

                    Divider(modifier = androidx.compose.ui.Modifier.padding(vertical = 8.dp), color = Color.Black, thickness = 1.dp)
                    Row(
                        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
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
                    Button(
                        onClick = {goToPago()},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF738AFF),
                            contentColor = Color.White
                        ),
                        modifier = androidx.compose.ui.Modifier.padding(top = 8.dp).fillMaxWidth()
                    ) {
                        Text(
                            text = "Pagar $${uiState.total}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CarritoRow(carrito: CarritoDetalleEntity) {
    Card(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = androidx.compose.ui.Modifier
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
                Spacer(modifier = androidx.compose.ui.Modifier.height(4.dp))
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
        )
    )

    CarritoBodyScreen(
        uiState = uiState,
        goToPago = {},
        navController = NavHostController(LocalContext.current)
    )
}