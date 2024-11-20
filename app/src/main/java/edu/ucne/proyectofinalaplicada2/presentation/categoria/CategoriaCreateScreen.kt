package edu.ucne.proyectofinalaplicada2.presentation.categoria

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.proyectofinalaplicada2.presentation.components.CustomTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.OpcionTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import android.util.Base64

@Composable
fun CategoriaCreateScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit
) {
    val context = LocalContext.current
    var nombreCategoria by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            val byteArray = context.contentResolver.openInputStream(uri)?.readBytes()
            byteArray?.let {

                val base64String = Base64.encodeToString(it, Base64.DEFAULT)
                viewModel.onUiEvent(CategoriaUiEvent.SetImagen(base64String))
            }
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
                opcion = OpcionTextField(
                    label = "Nombre de la categoría",
                    value = nombreCategoria,
                    error = "",
                    maxLines = 5
                ),
                onValueChange = {
                    nombreCategoria = it
                    viewModel.onUiEvent(CategoriaUiEvent.SetNombre(it))
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onImeAction = {},

            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Seleccionar Imagen")
            }

            Spacer(modifier = Modifier.height(16.dp))
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


            Button(
                onClick = {
                    if (nombreCategoria.isBlank()) {
                        viewModel.onUiEvent(CategoriaUiEvent.SetNombreError("El nombre de la categoría no puede estar vacío"))
                    } else if (selectedImageUri == null) {
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
