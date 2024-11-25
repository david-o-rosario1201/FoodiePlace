package edu.ucne.proyectofinalaplicada2.presentation.producto


import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import java.math.BigDecimal

@Composable
fun ProductoAddScreen(
    item: ProductoEntity
) {
    var count by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = " ",
                onBackClick = { /* Acción para el botón de retroceso */ }
            )
        },
        content = { paddingValues ->  // Aquí usamos el padding que nos da el Scaffold
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)  // Aplica el padding proporcionado por Scaffold
                    .padding(16.dp)  // Puedes agregar más relleno si es necesario
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Título y precio
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = " ${item.nombre}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = "${item.precio} RD", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
                }

                // Imagen de la empanada
                item.imagen?.let { imagenBase64 ->
                    val imagenByteArray = android.util.Base64.decode(imagenBase64, android.util.Base64.DEFAULT)
                    val imagenBitmap = BitmapFactory.decodeByteArray(imagenByteArray, 0, imagenByteArray.size)

                    imagenBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Imagen Producto",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(16.dp)
                        )
                    }
                }

                // Descripción
                Text(
                    text = " ${item.descripcion}",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Fila de botones
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.Center, // Centra los elementos
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón de disminución
                    Button(
                        onClick = { if (count > 1) count-- },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = color_oro,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .size(50.dp) // Hacer el botón más pequeño
                    ) {
                        Text(text = "-", color = Color.White, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre los botones

                    // Contador en el centro
                    Text(
                        text = count.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .width(50.dp) // Establece un ancho fijo para el contador
                            .align(Alignment.CenterVertically), // Centra el contador verticalmente
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre los botones

                    // Botón de aumento
                    Button(
                        onClick = { count++ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = color_oro,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .size(50.dp) // Hacer el botón más pequeño
                    ) {
                        Text(text = "+", color = Color.White, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el contador y el botón "Agregar"

                    // Botón de agregar
                    Button(
                        onClick = { /* Acción para agregar */ },
                        modifier = Modifier
                            .size(50.dp) // Hacer el botón más pequeño
                            .weight(1f)  // Esto hace que ocupe el espacio restante
                            .padding(horizontal = 8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = color_oro,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Agregar",
                            fontSize = 11.sp, // Hacer el texto más pequeño
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    )
}




@Preview(showBackground = true)
@Composable
fun ProductoAddScreenPreview() {
    // Se crea una instancia de ProductoEntity de ejemplo
    val producto = ProductoEntity(
        productoId = 1,
        nombre = "Empanada",
        descripcion = "Disfruta de nuestra exquisita empanada de queso, elaborada con una masa crujiente y dorada que encierra un delicioso relleno de quesos fundidos, creando una experiencia gastronómica irresistible. Cada bocado ofrece una mezcla perfecta de sabores y texturas, ideal para cualquier momento del día, ya sea como aperitivo, snack o plato principal.",
        precio = BigDecimal("29.99"),
        disponibilidad = false,
        imagen = "android.resource://edu.ucne.proyectofinalaplicada2/drawable/pizza.png",
        tiempo = "10 minutos",
        categoriaId = 2
    )
    ProductoAddScreen(item = producto)
}