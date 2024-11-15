package edu.ucne.proyectofinalaplicada2.presentation.categoria

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.proyectofinalaplicada2.presentation.components.CustomTextField
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.components.ImagePicker
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun CategoriaCreateScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit
) {
    val context = LocalContext.current // Obtener el contexto
    var nombreCategoria by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // ActivityResultLauncher para seleccionar imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            // Convertimos la imagen seleccionada en un ByteArray y lo pasamos al ViewModel
            val byteArray = context.contentResolver.openInputStream(uri)?.readBytes()
            byteArray?.let { viewModel.onUiEvent(CategoriaUiEvent.SetImagen(it)) }
        }
    }

    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = "Nueva categoría",
                onBackClick = onNavigateToList,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CustomTextField(
                label = "Nombre de la categoría",
                value = nombreCategoria,
                onValueChange = {
                    nombreCategoria = it
                    viewModel.onUiEvent(CategoriaUiEvent.SetNombre(it))
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onImeAction = {},
                error = ""
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para seleccionar la imagen
            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Seleccionar Imagen")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar la imagen seleccionada si existe
            selectedImageUri?.let { uri ->
                val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de guardar
            Button(
                onClick = {
                    if (nombreCategoria.isBlank()) {
                        // Mostrar un mensaje de error o alerta
                        viewModel.onUiEvent(CategoriaUiEvent.SetNombreError("El nombre de la categoría no puede estar vacío"))
                    } else if (selectedImageUri == null) {
                        // Mostrar un mensaje de error o alerta
                        viewModel.onUiEvent(CategoriaUiEvent.SetImagenError("Debe seleccionar una imagen"))
                    } else {
                        viewModel.onUiEvent(CategoriaUiEvent.Save)
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = color_oro,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp),
            ) {
                Text(
                    text = "Guardar",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

        }
    }
}
