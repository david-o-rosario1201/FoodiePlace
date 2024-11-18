package edu.ucne.proyectofinalaplicada2.presentation.carrito

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.navigation.BottomBarNavigation
import java.lang.reflect.Modifier

@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel = hiltViewModel(),
    navController: NavHostController,
    goToCarritoList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CarritoBodyScreen(
        uiState = uiState,
        goToCarritoList = goToCarritoList,
        navController = navController
    )
}

@Composable
private fun CarritoBodyScreen(
    uiState: CarritoUiState,
    goToCarritoList: () -> Unit,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Carrito",
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
    ){paddingValues ->
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ){

        }
    }
}
