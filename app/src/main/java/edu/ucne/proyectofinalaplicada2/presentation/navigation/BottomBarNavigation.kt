package edu.ucne.proyectofinalaplicada2.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro

@Composable
fun BottomBarNavigation(
    navController: NavHostController
) {
    var selectedItemIndex by remember { mutableStateOf("Home") }

    Surface(
        shape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp
        ),
        color = color_oro,
        modifier = Modifier.height(90.dp)
    ) {
        NavigationBar(
            containerColor = color_oro,
            modifier = Modifier.fillMaxWidth()
        ) {
            NavigationBarItem(
                label = { Text(text = "Home") },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == "Home") Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(24.dp) // Tamaño estándar para íconos
                    )
                },
                selected = selectedItemIndex == "Home",
                onClick = {
                    selectedItemIndex = "Home"
                    navController.navigate(Screen.HomeScreen)
                }
            )
            NavigationBarItem(
                label = { Text(text = "Carrito") },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == "Carrito") Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                        contentDescription = "Carrito",
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedItemIndex == "Carrito",
                onClick = {
                    selectedItemIndex = "Carrito"
                    navController.navigate(Screen.CarritoListScreen)
                }
            )
            NavigationBarItem(
                label = { Text(text = "Pedidos") },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == "Pedidos") Icons.Filled.DateRange else Icons.Outlined.DateRange,
                        contentDescription = "Pedidos",
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedItemIndex == "Pedidos",
                onClick = {
                    selectedItemIndex = "Pedidos"
                    navController.navigate(Screen.PedidoListScreen)
                }
            )
            NavigationBarItem(
                label = { Text(text = "Reviews") },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == "Reviews") Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Reviews",
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedItemIndex == "Reviews",
                onClick = {
                    selectedItemIndex = "Review"
                    navController.navigate(Screen.ReviewListScreen)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomBarNavigationPreview(){
    ProyectoFinalAplicada2Theme {
        BottomBarNavigation(navController = NavHostController(LocalContext.current))
    }
}