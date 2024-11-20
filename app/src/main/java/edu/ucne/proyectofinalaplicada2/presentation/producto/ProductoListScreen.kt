package edu.ucne.proyectofinalaplicada2.presentation.producto

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import java.math.BigDecimal

@Composable
fun ProductosListScreen(
    viewModel: ProductoViewModel = hiltViewModel(),
    goToProducto: (Int) -> Unit,
    onAddProducto: () -> Unit,
    modifier: Modifier = Modifier,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductosListBodyScreen(
        uiState = uiState,
        goToProducto = goToProducto,
        onAddProducto = onAddProducto,
        modifier = modifier,
        onDrawer = onDrawer
    )
}

@Composable
fun ProductosListBodyScreen(
    uiState: ProductoUiState,
    goToProducto: (Int) -> Unit,
    onAddProducto: () -> Unit,
    modifier: Modifier = Modifier,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Productos",
                onClickMenu = onDrawer,
                onClickNotifications = {},
                notificationCount = 0
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProducto,
                containerColor = color_oro,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Agregar nuevo producto")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Listado de Productos",
                color = Color(0xFFFFA500),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(uiState.productos) { producto ->
                    ProductoItem(
                        item = producto,
                        goToProducto = goToProducto
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProductoItem(
    item: ProductoEntity,
    goToProducto: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { goToProducto(item.productoId) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del producto en la parte izquierda
            item.imagen?.let { imagenBase64 ->
                val imagenByteArray = android.util.Base64.decode(imagenBase64, android.util.Base64.DEFAULT)
                val imagenBitmap = BitmapFactory.decodeByteArray(imagenByteArray, 0, imagenByteArray.size)

                imagenBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Imagen Producto",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Detalles del producto
            Column {
                Text(text = "Producto: ${item.nombre}", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Precio: ${item.precio} USD", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Descripción: ${item.descripcion}", fontSize = 14.sp, color = Color.Gray)
                Text(
                    text = " ${if (item.disponibilidad) "Disponible" else "No disponible"}",
                    fontSize = 14.sp,
                    color = if (item.disponibilidad) Color(0xFF4CAF50) else Color(0xFFFF0000)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductosListScreenPreview() {
    val sampleProductos = listOf(
        ProductoEntity(
            productoId = 1,
            nombre = "Producto A",
            categoriaId = 1,
            descripcion = "Descripción de producto A",
            precio = BigDecimal("19.99"),
            disponibilidad = true,
            imagen = "android.resource://edu.ucne.proyectofinalaplicada2/drawable/pizza.png"
        ),
        ProductoEntity(
            productoId = 2,
            nombre = "Producto B",
            categoriaId = 2,
            descripcion = "Descripción de producto B",
            precio = BigDecimal("29.99"),
            disponibilidad = false,
            imagen = "android.resource://edu.ucne.proyectofinalaplicada2/drawable/pizza.png"
        )
    )

    ProductosListBodyScreen(
        uiState = ProductoUiState(productos = sampleProductos),
        goToProducto = {},
        onAddProducto = {},
        onDrawer = {}
    )
}
