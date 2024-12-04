package edu.ucne.proyectofinalaplicada2.presentation.navigation

import CategoriaListScreen
import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.proyectofinalaplicada2.presentation.Home.HomeScreen
import edu.ucne.proyectofinalaplicada2.presentation.Reseñas.ReviewCreateScreen
import edu.ucne.proyectofinalaplicada2.presentation.Reseñas.ReviewListScreen
import edu.ucne.proyectofinalaplicada2.presentation.aboutus.AboutUsScreen
import edu.ucne.proyectofinalaplicada2.presentation.carrito.CarritoScreen
import edu.ucne.proyectofinalaplicada2.presentation.categoria.CategoriaCreateScreen
import edu.ucne.proyectofinalaplicada2.presentation.notificacion.NotificacionScreen
import edu.ucne.proyectofinalaplicada2.presentation.oferta.OfertaListScreen
import edu.ucne.proyectofinalaplicada2.presentation.oferta.OfertaScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoAdminScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoClienteScreen
import edu.ucne.proyectofinalaplicada2.presentation.pedido.PedidoListScreen
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoAddCarritoScreen
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoScreen
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoViewModel
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductosListScreen
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductosPorCategoriaScreen
import edu.ucne.proyectofinalaplicada2.presentation.reservaciones.ReservacionesListScreen
import edu.ucne.proyectofinalaplicada2.presentation.reservaciones.ReservacionesScreenCliente
import edu.ucne.proyectofinalaplicada2.presentation.sign_in.GoogleAuthUiClient
import edu.ucne.proyectofinalaplicada2.presentation.tarjeta.TarjetaScreen
import edu.ucne.proyectofinalaplicada2.presentation.usuario.ProfileScreen
import edu.ucne.proyectofinalaplicada2.presentation.usuario.UsuarioLoginScreen
import edu.ucne.proyectofinalaplicada2.presentation.usuario.UsuarioRegisterScreen
import edu.ucne.proyectofinalaplicada2.presentation.usuario.UsuarioViewModel
import edu.ucne.proyectofinalaplicada2.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.launch

@Composable
fun FoodiePlaceNavHost(
    navHostController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                    navHostController = navHostController
                )
            }
            composable<Screen.UsuarioLoginScreen>{
                val viewModel: UsuarioViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                // sonar-ignore: launcher is used indirectly in onSignUsuarioClick
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = {result ->
                        if(result.resultCode == RESULT_OK){
                            scope.launch {
                                val signInResult =
                                    googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                viewModel.onSignInResult(signInResult)
                                navHostController.navigate(Screen.HomeScreen){
                                    popUpTo(Screen.UsuarioLoginScreen) { inclusive = true }
                                }
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful){
                    if(state.isSignInSuccessful){
                        Toast.makeText(
                            context,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                UsuarioLoginScreen(
                    onRegisterUsuario = {
                        navHostController.navigate(Screen.UsuarioRegisterScreen)
                    },
                    onSignClickNative = {
                        navHostController.navigate(Screen.HomeScreen){
                            popUpTo(Screen.UsuarioLoginScreen) { inclusive = true }
                        }
                    },
                    onSignClickWithGoogle = {
                        scope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )
            }
            composable<Screen.UsuarioRegisterScreen>{
                UsuarioRegisterScreen(
                    onLoginUsuario = {
                        navHostController.navigate(Screen.UsuarioLoginScreen)
                    },
                    onNavigateToHome = {
                        navHostController.navigate(Screen.HomeScreen) {
                            popUpTo(Screen.UsuarioRegisterScreen) { inclusive = true }
                        }
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
            composable<Screen.CarritoListScreen>{
                CarritoScreen(
                    navController = navHostController,
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
            composable<Screen.PedidoListScreen>{
               PedidoListScreen(
                   onClickClientePedido = {
                       navHostController.navigate(Screen.PedidoClienteScreen)
                   },
                   onClickAdminPedido = {
                       navHostController.navigate(Screen.PedidoAdminScreen)
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
            composable<Screen.ReviewListScreen>{
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
            composable<Screen.ReviewCreateScreen>{
                ReviewCreateScreen(
                    onNavigateToList = {
                        navHostController.navigate(Screen.ReviewListScreen)
                    }
                )
            }
            composable<Screen.HomeScreen>{
                HomeScreen(
                    navController = navHostController,
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onClickNotifications = {}
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
            composable<Screen.NotificacionScreen> {
                NotificacionScreen(
                    goToHome = {
                        navHostController.navigate(Screen.HomeScreen)
                    }
                )
            }
            composable<Screen.ProductoScreen> {
                ProductoScreen(
                    onProductoCreado = {
                        navHostController.navigate(Screen.ProductoScreen)
                    },
                    onBackClick = {
                        navHostController.navigate(Screen.ProductoListScreen)
                    }
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
                    },
                    onClickNotifications = {
                        navHostController.navigate(Screen.NotificacionScreen)
                    }
                )
            }
            composable<Screen.ReservacionListScreen> {
                ReservacionesListScreen(
                    goToReservacion = {
                        navHostController.navigate(Screen.ReservacionesScreenCliente)  // Navegar a la pantalla de crear reservación
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

            composable<Screen.ReservacionesScreenCliente> {
                ReservacionesScreenCliente(
                    onNavigateToList = {
                        navHostController.navigate(Screen.ReservacionListScreen)
                    },
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

            composable<Screen.ProfileScreen> {
                ProfileScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onSignOut = {
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()
                        navHostController.navigate(Screen.UsuarioLoginScreen){
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<Screen.ProductosPorCategoriaScreen> {
                val viewModel = hiltViewModel<ProductoViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ProductosPorCategoriaScreen(
                    uiState = uiState,
                    onNavigateToList = {
                        navHostController.navigate(Screen.CategoriaListScreen)
                    }
                )
            }

            composable<Screen.ProductoAddCarritoScreen>
            {
                ProductoAddCarritoScreen(
                    onBackClick = {
                        navHostController.navigate(Screen.ProductoListScreen)
                    },
                    onAddToCart = { _, _ -> }
                )
            }

            composable<Screen.TarjetaScreen> {
                TarjetaScreen(
                    onNavigateToList = {
                        navHostController.navigate(Screen.TarjetaScreen)
                    }
                )
            }

        }
    }
}