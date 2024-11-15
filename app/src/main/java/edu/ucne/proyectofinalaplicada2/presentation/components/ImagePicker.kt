package edu.ucne.proyectofinalaplicada2.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ImagePicker(onImageSelected: (ByteArray?) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val byteArray = inputStream?.readBytes()
            onImageSelected(byteArray) // Devuelve el byteArray con la imagen seleccionada
        }
    }

    Button(onClick = { launcher.launch("image/*") }) {
        Text("Seleccionar imagen")
    }
}
