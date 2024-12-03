package edu.ucne.proyectofinalaplicada2.presentation.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.carrito.CarritoUiEvent
import edu.ucne.proyectofinalaplicada2.presentation.carrito.CarritoViewModel
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaUiState
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.navigation.BottomBarNavigation
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiState
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import kotlinx.coroutines.launch
import java.math.BigDecimal

@Composable
fun HomeScreen(
    goCategoria: () -> Unit,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    carritoViewModel: CarritoViewModel = hiltViewModel(),
) {
    homeViewModel.getCurrentUser()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    if (uiState.usuarioRol == "Admin") {
        HomeAdminBodyScreen(
            uiState = uiState,
            goCategoria = goCategoria,
            goProducto = {},
            navController = navController,
            goOferta = {}

        )
    } else {
        // Vista del cliente
        HomeBodyScreen(
            uiState = uiState,
            goCategoria = goCategoria,
            onSearchQueryChanged = { homeViewModel.onSearchQueryChanged(it) },
            navController = navController,
            onCarritoEvent = { event ->
                coroutineScope.launch {
                    carritoViewModel.onUiEvent(event)
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBodyScreen(
    uiState: HomeUiState,
    goCategoria: () -> Unit,
    navController: NavHostController,
    onSearchQueryChanged: (String) -> Unit,
    onCarritoEvent: (CarritoUiEvent) -> Unit,
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    onSearchQueryChanged(query)
                },
                label = { Text("Buscar producto") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
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
                    CardItem(
                        nombre = categoria.nombre,
                        imagen = categoria.imagen,
                        color = color,
                        onButtonClick = { goCategoria() }
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
                    CardItem(
                        nombre = producto.nombre ?: "Sin nombre",
                        descripcion = "Precio: ${producto.precio}",
                        imagen = producto.imagen,
                        showButton = true,
                        onButtonClick = {
                            val carritoDetalle = CarritoDetalleEntity(
                                carritoDetalleId = 0,
                                carritoId = 0,
                                productoId = producto.productoId,
                                cantidad = 1,
                                precioUnitario = producto.precio,
                                impuesto = BigDecimal.ZERO,
                                subTotal = producto.precio,
                                propina = BigDecimal.ZERO
                            )
                            onCarritoEvent(CarritoUiEvent.AgregarProducto(carritoDetalle, 1))
                        },
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeAdminBodyScreen(
    navController: NavHostController,
    uiState: HomeUiState,
    goCategoria: () -> Unit,
    goProducto: () -> Unit,
    goOferta: () -> Unit,

){
    Scaffold(

    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
        ){
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hola, ${uiState.usuarioNombre}",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight(700),
                )
            )
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
            productos = listOf(
                ProductoEntity(
                    productoId = 1,
                    nombre = "Producto 1",
                    categoriaId = 1,
                    descripcion = "Descripción del Producto 1",
                    precio = BigDecimal(100),
                    disponibilidad = true,
                    imagen = "https://via.placeholder.com/150",
                    tiempo = "14"
                ),
                ProductoEntity(
                    productoId = 2,
                    nombre = "Producto 2",
                    categoriaId = 2,
                    descripcion = "Descripción del Producto 2",
                    precio = BigDecimal(200),
                    disponibilidad = true,
                    imagen = "https://via.placeholder.com/150",
                    tiempo = "25"
                )
            )
        )
    )

    HomeBodyScreen(
        uiState = uiState,
        goCategoria = {},
        onSearchQueryChanged = {},
        navController = NavHostController(LocalContext.current),
        onCarritoEvent = {},
    )
}

