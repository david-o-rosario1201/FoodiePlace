package edu.ucne.proyectofinalaplicada2

import android.os.Bundle

import com.google.android.gms.auth.api.identity.Identity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.proyectofinalaplicada2.presentation.navigation.ProyectoFinalAplicada2NavHost
import edu.ucne.proyectofinalaplicada2.presentation.sign_in.GoogleAuthUiClient
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalAplicada2Theme {
                val navHost = rememberNavController()
                ProyectoFinalAplicada2NavHost(
                    navHostController = navHost,
                    googleAuthUiClient = googleAuthUiClient
                )
            }
        }
    }
}
