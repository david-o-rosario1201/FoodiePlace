package edu.ucne.proyectofinalaplicada2.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.ucne.proyectofinalaplicada2.R

@Composable
fun PasswordVisibilityToggle(
    passwordVisible: Boolean,
    onClick: () -> Unit
){
    IconButton(onClick = onClick) {
        val icon = if (passwordVisible) {
            painterResource(R.drawable.eye_close_up)
        } else {
            painterResource(R.drawable.close_eye)
        }
        Icon(painter = icon, contentDescription = "Visibility", modifier = Modifier.size(24.dp))
    }
}