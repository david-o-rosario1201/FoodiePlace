package edu.ucne.proyectofinalaplicada2.presentation.aboutus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun AboutUsScreen(
    onDrawer: () -> Unit,
    onClickNotifications: () -> Unit
){
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "",
                onClickMenu = onDrawer,
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                Text(
                    text = "About Us",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "En FoodiePlace, creemos que cada comida es una experiencia para disfrutar " +
                            "al máximo; por eso, seleccionamos ingredientes frescos y de calidad para crear " +
                            "platos que combinan tradición e innovación. Nos apasiona ofrecer un ambiente " +
                            "acogedor donde nuestros clientes puedan relajarse, celebrar y disfrutar de " +
                            "sabores auténticos que conectan con el alma. \n" +
                            "\n" +
                            "¡Bienvenidos a la familia FoodiePlace, donde cada bocado cuenta una historia!",
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Versión: 1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "© FoodiePlace Inc. 2024",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AboutUsScreenPreview(){
    ProyectoFinalAplicada2Theme {
        AboutUsScreen(
            onDrawer = {},
            onClickNotifications = {}
        )
    }
}