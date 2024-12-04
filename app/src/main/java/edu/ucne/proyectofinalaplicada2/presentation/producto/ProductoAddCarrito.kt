package edu.ucne.proyectofinalaplicada2.presentation.producto

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.carrito.CarritoUiEvent
import edu.ucne.proyectofinalaplicada2.presentation.carrito.CarritoViewModel
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import kotlinx.coroutines.launch
import java.math.BigDecimal

@Composable
fun ProductoAddCarritoScreen(
    productoId: Int,
    productoViewModel: ProductoViewModel = hiltViewModel(),
    carritoViewModel: CarritoViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    productoViewModel.getCurrentUser()
    val uiState by productoViewModel.productoState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    productoViewModel.getProductoById(productoId)

    ProductoAddBodyScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddToCart = {event ->
            coroutineScope.launch {
                carritoViewModel.onUiEvent(event)
            }
        }
    )
}




@Composable
fun ProductoAddBodyScreen(
    uiState: ProductoUiState,
    onBackClick: () -> Unit,
    onAddToCart: (CarritoUiEvent) -> Unit,
) {
    var count by remember { mutableStateOf(1) }
    val producto = uiState.productos.firstOrNull()

    if (producto != null) {
        Scaffold(
            topBar = {
                SimpleTopBarComponent(
                    title = producto.nombre,
                    onBackClick = onBackClick
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    ProductoAddItem(
                        item = producto
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { if (count > 1) count-- },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = color_oro,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.size(50.dp)
                        ) {
                            Text(text = "-", color = Color.White, fontSize = 18.sp)
                        }

                        Text(
                            text = count.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = { count++ },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = color_oro,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.size(50.dp)
                        ) {
                            Text(text = "+", color = Color.White, fontSize = 18.sp)
                        }

                        Button(
                            onClick = {
                                val carritoDetalle = CarritoDetalleEntity(
                                    carritoDetalleId = 0,
                                    carritoId = 0,
                                    productoId = producto.productoId,
                                    cantidad = count,
                                    precioUnitario = producto.precio,
                                    impuesto = BigDecimal.ZERO,
                                    subTotal = producto.precio,
                                    propina = BigDecimal.ZERO
                                )
                                onAddToCart(CarritoUiEvent.AgregarProducto(carritoDetalle, count))
                                onBackClick
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = color_oro,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Agregar",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    } else {
        Text(
            text = "Producto no encontrado.",
            fontSize = 18.sp,
            color = Color.Red
        )
    }
}


@Composable
fun ProductoAddItem(
    item: ProductoEntity,

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nombre y precio
        Text(
            text = item.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "${item.precio} RD",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800)
        )

        // Imagen
        item.imagen?.let { imagenBase64 ->
            val imagenByteArray = android.util.Base64.decode(imagenBase64, android.util.Base64.DEFAULT)
            val imagenBitmap = BitmapFactory.decodeByteArray(imagenByteArray, 0, imagenByteArray.size)

            imagenBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Imagen Producto",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                )
            }
        }

        // Descripción
        Text(
            text = item.descripcion,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductoAddCarritoScreenPreview() {
    val sampleProducto = listOf(
        ProductoEntity(
            productoId = 1,
            nombre = "Empanada",
            descripcion = "Empanada rellena de queso fundido",
            precio = BigDecimal("29.99"),
            disponibilidad = true,
            imagen = "",
            tiempo = "10 minutos",
            categoriaId = 2
        )
    )

    ProductoAddBodyScreen(
        uiState = ProductoUiState(productos = sampleProducto),
        onBackClick = {},
        onAddToCart = {}
    )
}
