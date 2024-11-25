package edu.ucne.proyectofinalaplicada2.presentation.producto

import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.producto.ProductoUiEvent.CategoriaIdChange
import java.math.BigDecimal

@Composable
fun ProductoScreen(
    viewModel: ProductoViewModel = hiltViewModel(),
    onProductoCreado: (ProductoEntity) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var categoriaExpanded by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = uri
            uri?.let {
                val byteArray = context.contentResolver.openInputStream(uri)?.readBytes()
                byteArray?.let {
                    val base64String = Base64.encodeToString(it, Base64.DEFAULT)
                    viewModel.onEvent(ProductoUiEvent.ImagenChange(base64String))

                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Crear Producto",
                onClickMenu = {},
                onClickNotifications = {},
                notificationCount = 0
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            verticalArrangement = Arrangement.Top // Asegura que los elementos se apilen desde el principio
        ) {
            // Contenedor de campos de entrada con espacio flexible
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Título de la pantalla
                Text(
                    text = "Nuevo Producto",
                    color = Color(0xFFFFA500),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre del producto
                OutlinedTextField(
                    value = uiState.nombre ?: "",
                    onValueChange = { viewModel.onEvent(ProductoUiEvent.NombreChange(it)) },
                    label = { Text("Nombre del Producto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                )
                if (uiState.errorNombre?.isNotBlank() == true) {
                    Text(
                        text = uiState.errorNombre?.lowercase()!!,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Descripción del producto
                OutlinedTextField(
                    value = uiState.descripcion ?: "",
                    onValueChange = { viewModel.onEvent(ProductoUiEvent.DescripcionChange(it)) },
                    label = { Text("Descripción del Producto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Precio del producto
                OutlinedTextField(
                    value = uiState.precio?.toString() ?: "", // Convertir BigDecimal a String para mostrarlo en el TextField
                    onValueChange = {
                        val precio = it.toBigDecimalOrNull() ?: BigDecimal.ZERO // Convertir String a BigDecimal o usar BigDecimal.ZERO si es nulo
                        viewModel.onEvent(ProductoUiEvent.PrecioChange(precio))
                    },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .clickable { categoriaExpanded = true },
                        label = { Text("Categoria") },
                        value = uiState.categoria.firstOrNull { it.categoriaId == uiState.categoriaId }?.nombre
                            ?: "",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.clickable { categoriaExpanded = true }
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = categoriaExpanded,
                        onDismissRequest = { categoriaExpanded = false }
                    ) {
                        uiState.categoria.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text(categoria.nombre) },
                                onClick = {
                                    viewModel.onEvent(CategoriaIdChange(categoria.categoriaId ?: 0))
                                    categoriaExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Disponibilidad
                Row(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Disponible")
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = uiState.disponibilidad ?: false,
                        onCheckedChange = { viewModel.onEvent(ProductoUiEvent.DisponibilidadChange(it)) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botón para seleccionar imagen
                Button(
                    onClick = {
                        Toast.makeText(context, "Seleccionar imagen", Toast.LENGTH_SHORT).show()
                        launcher.launch("image/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                ) {
                    Text(text = "Seleccionar Imagen")
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            // Botón para guardar el producto
            Button(
                onClick = {
                    viewModel.onEvent(ProductoUiEvent.Save)
                    if (uiState.success) {
                        val nuevoProducto = ProductoEntity(
                            productoId = uiState.productoId ?: 0,
                            nombre = uiState.nombre ?: "",
                            categoriaId = uiState.categoriaId ?: 1,
                            descripcion = uiState.descripcion ?: "",
                            precio = uiState.precio ?: BigDecimal.ZERO,
                            disponibilidad = uiState.disponibilidad ?: false,
                            imagen = uiState.imagen ?: "",
                            tiempo = uiState.tiempo ?: ""
                        )
                        onProductoCreado(nuevoProducto)
                        // Restablecer los valores de los campos
                        viewModel.onEvent(ProductoUiEvent.RestablecerCampos)



                    }
                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
            ) {
                Text(text = "Guardar Producto")
            }

        }
    }
}
