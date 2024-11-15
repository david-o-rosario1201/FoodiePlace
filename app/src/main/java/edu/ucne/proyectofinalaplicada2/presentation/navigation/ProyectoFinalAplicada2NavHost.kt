package edu.ucne.proyectofinalaplicada2.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.proyectofinalaplicada2.presentation.oferta.OfertaListScreen
import edu.ucne.proyectofinalaplicada2.presentation.oferta.OfertaScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoAdminScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoClienteScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoListScreen
import edu.ucne.proyectofinalaplicada2.presentation.usuario.UsuarioLoginScreen
import edu.ucne.proyectofinalaplicada2.presentation.usuario.UsuarioRegisterScreen
import edu.ucne.proyectofinalaplicada2.presentation.welcome.WelcomeScreen

@Composable
fun ProyectoFinalAplicada2NavHost(
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.WelcomeScreen
        ) {
            composable<Screen.WelcomeScreen>{
                WelcomeScreen(
                    onNavigateToLogin = {
                        navHostController.navigate(Screen.UsuarioLoginScreen)
                    }
                )
            }
            composable<Screen.UsuarioLoginScreen>{
                UsuarioLoginScreen(
                    onRegisterUsuario = {
                        navHostController.navigate(Screen.UsuarioRegisterScreen)
                    }
                )
            }
            composable<Screen.UsuarioRegisterScreen>{
                UsuarioRegisterScreen(
                    onLoginUsuario = {
                        navHostController.navigate(Screen.UsuarioLoginScreen)
                    }
                )
            }
            composable<Screen.OfertaListScreen>{
                OfertaListScreen(
                    onAddOferta = {
                        navHostController.navigate(Screen.OfertaScreen(0))
                    },
                    onClickOferta = {
                        navHostController.navigate(Screen.OfertaScreen(it))
                    }
                )
            }
            composable<Screen.OfertaScreen> { argumentos ->
                val id = argumentos.toRoute<Screen.OfertaScreen>().ofertaId

                OfertaScreen(
                    ofertaId = id,
                    goToOfertaList = {
                        navHostController.navigate(Screen.OfertaListScreen)
                    }
                )
            }
            composable<Screen.PedidoListScreen>{
               PedidoListScreen(
                   onClickPedido = {
                       navHostController.navigate(Screen.ReviewListScreen)
                   }
               )
            }
            composable<Screen.PedidoAdminScreen>{
                PedidoAdminScreen(
                    goToPedidoList = {
                        navHostController.navigate(Screen.PedidoListScreen)
                    }
                )
            }
            composable<Screen.PedidoClienteScreen>{
                PedidoClienteScreen(
                    goToPedidoList = {
                        navHostController.navigate(Screen.PedidoListScreen)
                    }
                )
            }
        }
    }
}