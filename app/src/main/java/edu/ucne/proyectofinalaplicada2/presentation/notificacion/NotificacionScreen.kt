package edu.ucne.proyectofinalaplicada2.presentation.notificacion

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.sampleNotificacionUiState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NotificacionScreen(
    viewModel: NotificacionViewModel = hiltViewModel(),
    goToHome: () -> Unit
){
    viewModel.getCurrentUser()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    NotificacionBodyScreen(
        uiState = uiState,
        goToHome = goToHome
    )
}

@Composable
fun NotificacionBodyScreen(
    uiState: NotificacionUiState,
    goToHome: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = "Notificaciones",
                onBackClick = goToHome
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(uiState.notificaciones) { uiState ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo FoodiePlace",
                            modifier = Modifier.size(50.dp)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 10.dp)
                        ) {
                            Text(
                                text = "FoodiePlace",
                                color = Color.Black,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = uiState.descripcion,
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text(
                            text = uiState.fecha.let { dateFormat.format(it) } ?: "",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun showNotification(
    context: Context,
    text: String,
    title: String,
    hasNotificationPermission: Boolean =
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
) {
    if (hasNotificationPermission) {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentText(text)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.logo)
            .build()
        notificationManager.notify(1, notification)
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun NotificacionBodyScreenPreview(){
    ProyectoFinalAplicada2Theme {
        NotificacionBodyScreen(
            uiState = sampleNotificacionUiState,
            goToHome = {}
        )
    }
}