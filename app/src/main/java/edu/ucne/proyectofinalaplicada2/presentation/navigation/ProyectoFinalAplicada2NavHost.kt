package edu.ucne.proyectofinalaplicada2.presentation.navigation

import CategoriaListScreen
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.proyectofinalaplicada2.presentation.Home.HomeScreen
import edu.ucne.proyectofinalaplicada2.presentation.Reseñas.ReviewCreateScreen
import edu.ucne.proyectofinalaplicada2.presentation.oferta.OfertaListScreen
import edu.ucne.proyectofinalaplicada2.presentation.oferta.OfertaScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoAdminScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoClienteScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoListScreen
import edu.ucne.proyectofinalaplicada2.presentation.Reseñas.ReviewListScreen
import edu.ucne.proyectofinalaplicada2.presentation.aboutus.AboutUsScreen
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaCreateScreen
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoScreen
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductosListScreen
import edu.ucne.proyectofinalaplicada2.presentation.reservaciones.ReservacionesListScreen
import edu.ucne.proyectofinalaplicada2.presentation.reservaciones.ReservacionesScreenCliente
import edu.ucne.proyectofinalaplicada2.presentation.usuario.UsuarioLoginScreen
import edu.ucne.proyectofinalaplicada2.presentation.usuario.UsuarioRegisterScreen
import edu.ucne.proyectofinalaplicada2.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.launch

@Composable
fun ProyectoFinalAplicada2NavHost(
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.ReservacionListScreen
        ) {
            composable<Screen.WelcomeScreen> {
                WelcomeScreen(
                    onNavigateToLogin = {
                        navHostController.navigate(Screen.UsuarioLoginScreen)
                    }
                )
            }
            composable<Screen.UsuarioLoginScreen> {
                UsuarioLoginScreen(
                    onRegisterUsuario = {
                        navHostController.navigate(Screen.CategoriaListScreen)
                    }
                )
            }
            composable<Screen.UsuarioRegisterScreen> {
                UsuarioRegisterScreen(
                    onLoginUsuario = {
                        navHostController.navigate(Screen.UsuarioLoginScreen)
                    }
                )
            }
            composable<Screen.OfertaListScreen> {
                OfertaListScreen(
                    onAddOferta = {
                        navHostController.navigate(Screen.OfertaScreen(0))
                    },
                    onClickOferta = {
                        navHostController.navigate(Screen.OfertaScreen(it))
                    },
                    onClickNotifications = {
                        navHostController.navigate(Screen.NotificacionScreen)
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
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
            composable<Screen.PedidoListScreen> {
                PedidoListScreen(
                    onClickPedido = {
                        navHostController.navigate(Screen.ReviewListScreen)
                    },
                    onClickNotifications = {
                        navHostController.navigate(Screen.NotificacionScreen)
                    },
                    navHostController = navHostController,
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }

                )
            }
            composable<Screen.PedidoListScreen>{
               PedidoListScreen(
                   onClickPedido = {
                       navHostController.navigate(Screen.ReviewListScreen)
                   },
                   onClickNotifications = {
                       navHostController.navigate(Screen.NotificacionScreen)
                   },
                   navHostController = navHostController,
                   onDrawer = {
                       scope.launch {
                           drawerState.open()
                       }
                   }
               )
            }
            composable<Screen.PedidoAdminScreen> {
                PedidoAdminScreen(
                    goToPedidoList = {
                        navHostController.navigate(Screen.PedidoListScreen)
                    }
                )
            }
            composable<Screen.PedidoClienteScreen> {
                PedidoClienteScreen(
                    goToPedidoList = {
                        navHostController.navigate(Screen.PedidoListScreen)
                    }
                )
            }
            composable<Screen.ReviewListScreen> {
                ReviewListScreen(
                    goToAddReview = {
                        navHostController.navigate(Screen.ReviewCreateScreen)
                    },
                    onClickNotifications = {
                        navHostController.navigate(Screen.NotificacionScreen)
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable<Screen.ReviewCreateScreen> {
                ReviewCreateScreen(
                    onNavigateToList = {
                        navHostController.navigate(Screen.ReviewListScreen)
                    }
                )
            }
            composable<Screen.CategoriaListScreen> {}
            composable<Screen.HomeScreen>{
                HomeScreen(
                    goCategoria = {
                        navHostController.navigate(Screen.UsuarioLoginScreen)
                    },
                    goProducto = {
                        navHostController.navigate(Screen.PedidoListScreen)
                    },
                    usuarioId = 1,
                    navController = navHostController,
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable<Screen.CategoriaListScreen>{
                CategoriaListScreen(
                    goToAddCategoria = {
                        navHostController.navigate(Screen.CategoriaCreateScreen)
                    },
                    onClickNotifications = {
                        navHostController.navigate(Screen.NotificacionScreen)
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable<Screen.CategoriaCreateScreen> {
                CategoriaCreateScreen(
                    onNavigateToList = {
                        navHostController.navigate(Screen.CategoriaListScreen)
                    }
                )
            }









            composable<Screen.AboutUsScreen> {
                AboutUsScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()

                        }
                    },
                    onClickNotifications = {
                        navHostController.navigate(Screen.NotificacionScreen)
                    }
                )
            }
            composable<Screen.ReservacionesScreenCliente> {
                ReservacionesScreenCliente(
                    onNavigateToList = {
                        navHostController.navigate(Screen.ReservacionesScreenCliente)
                    },
                )
            }
            composable<Screen.ProductoListScreen> {
                ProductosListScreen(
                    onAddProducto = {
                        navHostController.navigate(Screen.ProductoScreen)
                    },
                    goToProducto = {
                        navHostController.navigate(Screen.ProductoScreen)
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
            composable<Screen.ReservacionListScreen> {
                ReservacionesListScreen(

                    goToReservacion = {},
                    onClickNotifications = {
                        navHostController.navigate(Screen.NotificacionScreen)
                    },
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        }
    }
}