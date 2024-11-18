package edu.ucne.proyectofinalaplicada2.presentation.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaUiState
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.navigation.BottomBarNavigation
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiState
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import java.math.BigDecimal

@Composable
fun HomeScreen(
    usuarioId: Int,
    goProducto: () -> Unit,
    goCategoria: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {

    homeViewModel.loadUsuario(usuarioId)
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    HomeBodyScreen(
        uiState = uiState,
        goProducto = goProducto,
        goCategoria = goCategoria,
        onSearchQueryChanged = { homeViewModel.onSearchQueryChanged(it) },
        navController = navController
    )
}

@Composable
fun HomeBodyScreen(
    uiState: HomeUiState,
    goProducto: () -> Unit,
    goCategoria: () -> Unit,
    navController: NavHostController,
    onSearchQueryChanged: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf(uiState.searchQuery) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopBarComponent(
                title = " ",
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
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hola, ${uiState.usuarioNombre}",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight(700),
                    color = color_oro,
                )
            )
            Text(
                text = "Comida y bebida",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black,
                )
            )
            // Actualizar el SearchBar con el query
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    onSearchQueryChanged(query)
                },
                label = { Text("Buscar producto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.categoriaUiState.categorias) { categoria ->
                    val color = getCategoryColor(categoria.nombre)
                    CategoriaCard(
                        nombre = categoria.nombre,
                        imagen = categoria.imagen,
                        color = color,
                        onClick = { goCategoria() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Productos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(uiState.productoUiState.productos) { producto ->
                    ProductoCard(
                        nombre = producto.nombre ?: "Sin nombre",
                        imagen = producto.imagen,
                        precio = producto.precio.toString(),
                        onClick = {
                            goProducto()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeBodyScreenPreview() {
    val uiState = HomeUiState(
        categoriaUiState = CategoriaUiState(
            categorias = listOf(
                CategoriaEntity(categoriaId = 1, nombre = "Electrónica", imagen = "imagenBase64"),
                CategoriaEntity(categoriaId = 2, nombre = "Ropa", imagen = "imagenBase64")
            )
        ),
        productoUiState = ProductoUiState(
            productos = listOf(ProductoEntity(
                productoId = 1,
                nombre = "Producto 1",
                categoriaId = 1,
                descripcion = "Descripción del Producto 1",
                precio = BigDecimal(100),
                disponibilidad = true,
                imagen = "https://via.placeholder.com/150"
            ),
                ProductoEntity(
                    productoId = 2,
                    nombre = "Producto 2",
                    categoriaId = 2,
                    descripcion = "Descripción del Producto 2",
                    precio = BigDecimal(200),
                    disponibilidad = true,
                    imagen = "https://via.placeholder.com/150"
                )
            )
        )
    )

    HomeBodyScreen(
        uiState = uiState,
        goProducto = {},
        goCategoria = {},
        onSearchQueryChanged = {},
        navController = NavHostController(LocalContext.current)
    )
}
