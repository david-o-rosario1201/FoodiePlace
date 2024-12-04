package edu.ucne.proyectofinalaplicada2.presentation.usuario

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun ProfileScreen(
    onDrawer: () -> Unit,
    onSignOut: () -> Unit,
    viewModel: UsuarioViewModel = hiltViewModel()
){
    viewModel.getCurrentUser()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProfileBodyScreen(
        uiState = uiState,
        onDrawer = onDrawer,
        onEvent = viewModel::onEvent,
        onSignOut = onSignOut
    )
}

@Composable
private fun ProfileBodyScreen(
    uiState: UsuarioUiState,
    onDrawer: () -> Unit,
    onEvent: (UsuarioUiEvent) -> Unit,
    onSignOut: () -> Unit
){
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Perfil",
                onClickMenu = onDrawer,
                onClickNotifications = {},
                notificationCount = 0
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(!uiState.fotoPerfil.isNullOrBlank()) {
                AsyncImage(
                    model = uiState.fotoPerfil,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.nombre ?: "",
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.correo ?: "",
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    onEvent(UsuarioUiEvent.Logout)
                    onSignOut()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.log_out),
                    contentDescription = "Logout",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign out",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview(){
    ProyectoFinalAplicada2Theme {
        ProfileBodyScreen(
            uiState = UsuarioUiState(),
            onEvent = {},
            onDrawer = {},
            onSignOut = {}
        )
    }
}