package edu.ucne.proyectofinalaplicada2.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucne.proyectofinalaplicada2.R
import kotlinx.coroutines.launch

@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
){
    val selectedItem = remember { mutableStateOf("Home") }
    val scope = rememberCoroutineScope()

    fun handleItemClick(destination: Screen, item: String) {
        navHostController.navigate(destination)
        selectedItem.value = item
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "FoodiePlace",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    item {

                        DrawerItem(
                            title = stringResource(R.string.drawer_home),
                            icon = Icons.Filled.Home,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_pedidos)
                        ) {
                            handleItemClick(Screen.HomeScreen, it)
                        }
                        DrawerItem(
                            title = stringResource(R.string.drawer_pedidos),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_pedidos)
                        ) {
                            handleItemClick(Screen.PedidoListScreen, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_aboutus),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_aboutus)
                        ) {
                            handleItemClick(Screen.AboutUsScreen, it)
                        }

                        DrawerItem(
                            title = stringResource(R.string.drawer_productos),
                            icon = Icons.Filled.Info,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_productos)
                        ) {
                            handleItemClick(Screen.ProductoListScreen, it)

                        }
                        DrawerItem(
                            title = stringResource(R.string.drawer_Reviews),
                            icon = Icons.Filled.Favorite,
                            isSelected = selectedItem.value == stringResource(R.string.drawer_Reviews)
                        ) {
                            handleItemClick(Screen.ReviewListScreen, it)
                        }
                    }
                }
            }
        },
        drawerState = drawerState
    ){
        content()
    }
}