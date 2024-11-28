package edu.ucne.proyectofinalaplicada2.presentation.producto

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import java.math.BigDecimal

@Composable
fun ProductoAddCarritoScreen(
    viewModel: ProductoViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAddToCart: (Int, Int) -> Unit // Producto ID y cantidad
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductoAddBodyScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddToCart = onAddToCart
    )
}

@Composable
fun ProductoAddBodyScreen(
    uiState: ProductoUiState,
    onBackClick: () -> Unit,
    onAddToCart: (Int, Int) -> Unit
) {
    var count by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = uiState.nombre.toString(),
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
                // Contenido de los productos en la parte superior
                uiState.productos.forEach { producto ->
                    ProductoAddItem(
                        item = producto,
                        onIncrease = { count++ },
                        onDecrease = { if (count > 1) count-- },
                        onAddToCart = { onAddToCart(uiState.productoId!!, count) }
                    )
                }
                Spacer(modifier = Modifier.weight(1f)) // Empuja los botones hacia el final

                // Botones en la parte inferior
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
                        onClick = { onAddToCart(uiState.productoId!!, count) },
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
}

@Composable
fun ProductoAddItem(
    item: ProductoEntity,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onAddToCart: () -> Unit
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
        onAddToCart = { _, _ -> }
    )
}
