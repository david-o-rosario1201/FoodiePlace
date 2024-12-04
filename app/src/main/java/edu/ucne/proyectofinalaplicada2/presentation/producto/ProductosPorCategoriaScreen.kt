package edu.ucne.proyectofinalaplicada2.presentation.producto

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent

@Composable
fun ProductosPorCategoriaScreen(
    uiState: ProductoUiState,
    onNavigateToList: () -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = "Productos",
                onBackClick = onNavigateToList
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.productos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay productos disponibles en esta categoría.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(uiState.productos) { producto ->
                    ProductoRow(producto)
                }
            }
        }
    }
}

@Composable
fun ProductoRow(producto: ProductoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            producto.imagen?.let { imagenBase64 ->
            val imagenByteArray = android.util.Base64.decode(imagenBase64, android.util.Base64.DEFAULT)
            val imagenBitmap = BitmapFactory.decodeByteArray(imagenByteArray, 0, imagenByteArray.size)

            imagenBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Imagen categoría",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(8.dp)
                )
            }
        }


            Text(
                text = producto.nombre ?: "Producto",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Precio: $${producto.precio}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductosPorCategoriaPreview() {
    val productosFicticios = listOf(
        ProductoEntity(
            productoId = 1,
            nombre = "Producto A",
            categoriaId = 1,
            descripcion = "Descripción del producto A",
            precio = 200.0.toBigDecimal(),
            disponibilidad = true,
            imagen = "android.resource://edu.ucne.proyectofinalaplicada2/drawable/pizza.png",
            tiempo = "2 días"
        ),
        ProductoEntity(
            productoId = 2,
            nombre = "Producto B",
            categoriaId = 1,
            descripcion = "Descripción del producto B",
            precio = 300.0.toBigDecimal(),
            disponibilidad = true,
            imagen = "android.resource://edu.ucne.proyectofinalaplicada2/drawable/pizza.png",
            tiempo = "3 días"
        )
    )

    val uiState = ProductoUiState(
        isLoading = false,
        productos = productosFicticios
    )

    ProductosPorCategoriaScreen(
        uiState = uiState,
        onNavigateToList = {}
    )
}


