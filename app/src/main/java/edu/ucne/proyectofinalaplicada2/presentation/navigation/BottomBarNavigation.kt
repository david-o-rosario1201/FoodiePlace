package edu.ucne.proyectofinalaplicada2.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import edu.ucne.proyectofinalaplicada2.ui.theme.Menu_bar_color
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun BottomBarNavigation(
    navController: NavHostController
){
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
            route = "HomeListScreen"
        ),
        BottomNavigationItem(
            title = "Carrito",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
            hasNews = false,
            badgeCount = 45,
            route = "CarritoListScreen"
        ),
        BottomNavigationItem(
            title = "Pedidos",
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
            hasNews = true,
            badgeCount = 2,
            route = "PedidoListScreen"
        ),
        BottomNavigationItem(
            title = "Review",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            hasNews = true,
            badgeCount = 2,
            route = "ReviewListScreen"
        )
    )

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    NavigationBar(
        containerColor = Menu_bar_color
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route) // Use the route parameter here for navigation
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: String = ""
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomBarNavigationPreview(){
    ProyectoFinalAplicada2Theme {
        BottomBarNavigation(navController = NavHostController(LocalContext.current))
    }
}