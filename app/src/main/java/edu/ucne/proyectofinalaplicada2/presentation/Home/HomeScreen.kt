package edu.ucne.proyectofinalaplicada2.presentation.Home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaUiState
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaViewModel
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiEvent
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiState
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoViewModel

@Composable
fun HomeScreen(
    goProducto: () -> Unit,
    gocategoria: () -> Unit,
    productoViewModel: ProductoViewModel = hiltViewModel(),
    categoriaViewModel: CategoriaViewModel = hiltViewModel()
){
    val productouiState by productoViewModel.uiState.collectAsStateWithLifecycle()
    val categoriauiState by categoriaViewModel.uiState.collectAsStateWithLifecycle()
    HomeBodyScreen(
        goProducto = goProducto,
        goCategoria = gocategoria,
        productoUiState = productouiState,
        categoriaUiState = categoriauiState,
        productoViewModel = productoViewModel,
        categoriaViewModel = categoriaViewModel
    )
}


@Composable
fun HomeBodyScreen(
    productoViewModel: ProductoViewModel,
    categoriaViewModel: CategoriaViewModel,
    goProducto: () -> Unit,
    goCategoria : () -> Unit,
    categoriaUiState: CategoriaUiState,
    productoUiState: ProductoUiState,
) {


        var searchQuery by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                    productoViewModel.onEvent(ProductoUiEvent.NombreChange(query))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categoriaUiState.categorias) { categoria ->
                    CategoriaCard(
                        nombre = categoria.nombre,
                        imagen = categoria.imagen,
                        onClick = {
                            productoViewModel.onEvent(ProductoUiEvent.CategoriaIdChange(categoria.categoriaId))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Carrusel de productos
            Text(
                text = "Productos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(productoUiState.productos) { producto ->
                    producto.nombre?.let {
                        ProductoCard(
                            nombre = it,
                            precio = producto.precio.toString(),
                            imagen = producto.imagen,
                            onClick = {
                                // Acción al seleccionar producto
                                productoViewModel.onEvent(ProductoUiEvent.SelectedProducto(producto.productoId))
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Buscar producto") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }

    @Composable
    fun CategoriaCard(nombre: String, imagen: String?, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .size(100.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    @Composable
    fun ProductoCard(nombre: String, precio: String, imagen: String?, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .width(150.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                // Aquí puedes usar una librería como Coil para cargar imágenes desde una URL
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Precio: $precio",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }


//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun HomeScreenPreview() {
//    // Datos ficticios para categorías
//    val categorias = listOf(
//        CategoriaEntity(categoriaId = 1, nombre = "Electrónica", imagen = ""),
//        CategoriaEntity(categoriaId = 2, nombre = "Ropa", imagen = ""),
//        CategoriaEntity(categoriaId = 3, nombre = "Hogar", imagen = "")
//    )
//
//    // Datos ficticios para productos
//    val productos = listOf(
//        ProductoEntity(
//            productoId = 1, nombre = "Smartphone", categoriaId = 1,
//            descripcion = "Teléfono inteligente", precio = BigDecimal.valueOf(500.0),
//            disponibilidad = true, imagen = ""
//        ),
//        ProductoEntity(
//            productoId = 2, nombre = "Camiseta", categoriaId = 2,
//            descripcion = "Ropa de algodón", precio = BigDecimal.valueOf(20.0),
//            disponibilidad = true, imagen = ""
//        ),
//        ProductoEntity(
//            productoId = 3, nombre = "Silla", categoriaId = 3,
//            descripcion = "Silla ergonómica", precio = BigDecimal.valueOf(100.0),
//            disponibilidad = true, imagen = ""
//        )
//    )
//
//    // Renderizar la pantalla
//    HomeScreen(
//        goProducto = { /* Acción de navegación */ },
//        goCategoria = { /* Acción de navegación */ },
//        productoViewModel = { },
//        categoriaViewModel = fakeCategoriaViewModel,
//        productoUiState = productoUiState,
//        categoriaUiState = categoriaUiState
//    )
//}

