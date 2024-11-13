package edu.ucne.proyectofinalaplicada2.presentation.oferta

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Composable
fun OfertaListScreen(
    viewModel: OfertaViewModel = hiltViewModel(),
    onAddOferta: () -> Unit,
    onClickOferta: (Int) -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OfertaListBodyScreen(
        uiState = uiState,
        onAddOferta = onAddOferta,
        onClickOferta = onClickOferta
    )
}

@Composable
private fun OfertaListBodyScreen(
    uiState: OfertaUiState,
    onAddOferta: () -> Unit,
    onClickOferta: (Int) -> Unit
){
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Ofertas",
                onClickMenu = {},
                onClickNotifications = {},
                notificationCount = 0
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddOferta,
                contentColor = Color.White,
                containerColor = color_oro,
                modifier = Modifier.clip(RoundedCornerShape(50.dp)),
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nueva oferta"
                )
            }
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            if(uiState.ofertas.isEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        painter = painterResource(R.drawable.empty_icon),
                        contentDescription = "Lista vacía"
                    )
                    Text(
                        text = "Lista vacía",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ){
                    items(uiState.ofertas){
                        OfertaRow(
                            it = it,
                            productos = uiState.productos,
                            onClickOferta = onClickOferta
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OfertaRow(
    it: OfertaEntity,
    productos: List<ProductoEntity>,
    onClickOferta: (Int) -> Unit
) {
    val producto = productos.find { p -> p.productoId == it.productoId }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onClickOferta(it.ofertasId ?: 0) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            // Imagen del producto
            Card(
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 10.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.pizza),
                    contentDescription = null
                )
            }

            // Información del producto y las fechas
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto?.nombre ?: "Pizza PP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = "${dateFormat.format(it.fechaInicio)} - ${dateFormat.format(it.fechaFinal)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Precios
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$.${it.precioOferta}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Green,
                    fontSize = 20.sp,
                    //fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$.${it.precio}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}

private val sampleUiState = OfertaUiState(
    ofertas = listOf(
        OfertaEntity(
            ofertasId = 1,
            productoId = 1,
            precio = BigDecimal.valueOf(100),
            precioOferta = BigDecimal.valueOf(50),
            fechaInicio = Date.from(Instant.now()),
            fechaFinal = Date.from(Instant.now()),
            descuento = BigDecimal.valueOf(50),
            imagen = 0
        ),
        OfertaEntity(
            ofertasId = 2,
            productoId = 2,
            precio = BigDecimal.valueOf(150),
            precioOferta = BigDecimal.valueOf(75.0),
            fechaInicio = Date.from(Instant.now()),
            fechaFinal = Date.from(Instant.now()),
            descuento = BigDecimal.valueOf(75.0),
            imagen = 0
        )
    )
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OfertaListScreenPreview(){
    ProyectoFinalAplicada2Theme {
        OfertaListBodyScreen(
            uiState = sampleUiState,
            onAddOferta = {},
            onClickOferta = {}
        )
    }
}