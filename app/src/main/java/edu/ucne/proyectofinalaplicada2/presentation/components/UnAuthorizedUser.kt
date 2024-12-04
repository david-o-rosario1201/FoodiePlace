package edu.ucne.proyectofinalaplicada2.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import edu.ucne.proyectofinalaplicada2.R

@Composable
fun UnAuthorizedUser(
    onDrawer: () -> Unit,
    onClickNotifications: () -> Unit
){
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "No autorizado",
                onClickMenu = onDrawer,
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                painter = painterResource(R.drawable.persona_no_autorizada),
                contentDescription = "Usuario no autorizado"
            )
            Text(
                text = "Usuario no autorizado",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}