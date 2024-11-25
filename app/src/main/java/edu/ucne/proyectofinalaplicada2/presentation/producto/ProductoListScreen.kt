package edu.ucne.proyectofinalaplicada2.presentation.producto

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.PullToRefreshLazyColumn
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        onEvent = viewModel::onEvent,
        goToProducto = goToProducto,
        onAddProducto = onAddProducto,
        modifier = modifier,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosListBodyScreen(
    uiState: ProductoUiState,
    onEvent: (ProductoUiEvent) -> Unit,
    goToProducto: (Int) -> Unit,
    onAddProducto: () -> Unit,
    modifier: Modifier = Modifier,
    onDrawer: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            PullToRefreshLazyColumn(
                isRefreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        onEvent(ProductoUiEvent.Refresh)
                        delay(3000L)
                        isRefreshing = false
                    }
                }
            ){
                if (uiState.productos.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(
                            painter = painterResource(R.drawable.empty_icon),
                            contentDescription = "Lista vacía"
                        )
                        Text(
                            text = "Lista vacía",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    uiState.productos.forEach { producto ->
                        ProductoItem(
                            item = producto,
                            goToProducto = goToProducto
                        )
                    }
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

            Column {
                Text(text = "Producto: ${item.nombre}", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Precio: ${item.precio} USD", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Descripción: ${item.descripcion}", fontSize = 14.sp, color = Color.Gray)
                Text(
                    text = " ${if (item.disponibilidad) "Disponible" else "No disponible"}",
                    fontSize = 14.sp,
                    color = if (item.disponibilidad) Color(0xFF4CAF50) else Color(0xFFFF0000)
                )
                Text(text = "Tiempo:  ${item.tiempo}", fontSize = 16.sp, color = Color.Gray)
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
            disponibilidad = true,imagen = "android.resource://edu.ucne.proyectofinalaplicada2/drawable/pizza.png",
            tiempo = "15 minutos"
        ),
        ProductoEntity(
            productoId = 2,
            nombre = "Producto B",
            categoriaId = 2,
            descripcion = "Descripción de producto B",
            precio = BigDecimal("29.99"),
            disponibilidad = false,
            imagen = "android.resource://edu.ucne.proyectofinalaplicada2/drawable/pizza.png",
            tiempo = "10 minutos"
        )
    )

    ProductosListBodyScreen(
        uiState = ProductoUiState(productos = sampleProductos),
        goToProducto = {},
        onAddProducto = {},
        onEvent = {},
        onDrawer = {}
    )
}