package edu.ucne.proyectofinalaplicada2.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        }
    }
}