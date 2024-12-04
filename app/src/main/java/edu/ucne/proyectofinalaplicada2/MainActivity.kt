package edu.ucne.proyectofinalaplicada2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.proyectofinalaplicada2.presentation.navigation.ProyectoFinalAplicada2NavHost
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalAplicada2Theme {
                val navHost = rememberNavController()
                ProyectoFinalAplicada2NavHost(navHost)
            }
        }
    }
}
