package edu.ucne.proyectofinalaplicada2.presentation.oferta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.CustomDropDown
import edu.ucne.proyectofinalaplicada2.presentation.components.CustomTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.DatePickerField
import edu.ucne.proyectofinalaplicada2.presentation.components.OpcionTextField
import edu.ucne.proyectofinalaplicada2.presentation.components.SimpleTopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import edu.ucne.proyectofinalaplicada2.ui.theme.color_oro
import java.util.Date

@Composable
fun OfertaScreen(
    viewModel: OfertaViewModel = hiltViewModel(),
    goToOfertaList: () -> Unit,
    ofertaId: Int
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OfertaBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goToOfertaList = goToOfertaList,
        ofertaId = ofertaId
    )
}

@Composable
private fun OfertaBodyScreen(
    uiState: OfertaUiState,
    onEvent: (OfertaUiEvent) -> Unit,
    goToOfertaList: () -> Unit,
    ofertaId: Int
) {
    val title = if (ofertaId == 0) "Nueva oferta" else "Editar oferta"
    val buttonIcon = if (ofertaId == 0) Icons.Default.Add else Icons.Default.Edit
    val buttonText = if (ofertaId == 0) "Guardar" else "Actualizar"
    val buttonContentDescription = if (ofertaId == 0) "Guardar" else "Actualizar"

    LaunchedEffect(key1 = true, key2 = uiState.isSuccess){
        onEvent(OfertaUiEvent.SelectedOferta(ofertaId))

        if(uiState.isSuccess){
            goToOfertaList()
        }
    }

    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = title,
                onBackClick = goToOfertaList
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ProductoDropDown(
                uiState = uiState,
                onEvent = onEvent
            )
            PrecioInputFields(
                uiState = uiState,
                onEvent = onEvent
            )
            DatePickers(
                uiState = uiState,
                onEvent = onEvent
            )
            SaveButton(
                buttonIcon = buttonIcon,
                buttonText = buttonText,
                buttonContentDescription = buttonContentDescription,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun ProductoDropDown(
    uiState: OfertaUiState,
    onEvent: (OfertaUiEvent) -> Unit
) {
    CustomDropDown(
        items = uiState.productos,
        selectedItemId = uiState.productoId,
        onEvent = onEvent,
        event = OfertaUiEvent.ProductoIdChanged(uiState.productoId ?: 0),
        itemToString = { it.nombre },
        itemId = { it.productoId }
    )
}

@Composable
private fun PrecioInputFields(
    uiState: OfertaUiState,
    onEvent: (OfertaUiEvent) -> Unit
) {
    CustomTextField(
        opcion = OpcionTextField(
            label = "Precio",
            value = uiState.precio.toString(),
            error = uiState.errorPrecio
        ),
        onValueChange = { onEvent(OfertaUiEvent.PrecioChanged(it.toBigDecimal())) },
        imeAction = ImeAction.Next,
        onImeAction = {}
    )
    CustomTextField(
        opcion = OpcionTextField(
            label = "Descuento",
            value = uiState.descuento.toString(),
            error = uiState.errorDescuento
        ),
        onValueChange = { onEvent(OfertaUiEvent.DescuentoChanged(it.toBigDecimal())) },
        imeAction = ImeAction.Next,
        onImeAction = {}
    )
    CustomTextField(
        opcion = OpcionTextField(
            label = "Precio oferta",
            value = uiState.precioOferta.toString(),
            error = uiState.errorPrecioOferta
        ),
        onValueChange = { onEvent(OfertaUiEvent.PrecioOfertaChanged(it.toBigDecimal())) },
        imeAction = ImeAction.Next,
        onImeAction = {}
    )
}

@Composable
private fun DatePickers(
    uiState: OfertaUiState,
    onEvent: (OfertaUiEvent) -> Unit
) {
    DatePickerField(
        onEvent = onEvent,
        selectedDate = uiState.fechaInicio,
        event = OfertaUiEvent.FechaInicioChanged(uiState.fechaInicio ?: Date())
    )
    DatePickerField(
        onEvent = onEvent,
        selectedDate = uiState.fechaFinal,
        event = OfertaUiEvent.FechaFinalChanged(uiState.fechaFinal ?: Date())
    )
}

@Composable
private fun SaveButton(
    buttonIcon: ImageVector,
    buttonText: String,
    buttonContentDescription: String,
    onEvent: (OfertaUiEvent) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onEvent(OfertaUiEvent.Save) },
            colors = ButtonColors(
                containerColor = color_oro,
                contentColor = color_oro,
                disabledContainerColor = color_oro,
                disabledContentColor = color_oro
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = buttonIcon,
                contentDescription = buttonContentDescription,
                tint = Color.White
            )
            Text(
                text = buttonText,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}


private val sampleUiState = OfertaUiState(
    productoId = 1,
    precio = 100.toBigDecimal(),
    descuento = 10.toBigDecimal(),
    precioOferta = 90.toBigDecimal(),
    fechaInicio = Date(),
    fechaFinal = Date(),
    productos = listOf(
        ProductoEntity(
            productoId = 1,
            precio = 100.toBigDecimal(),
            nombre = "Cocacola",
            categoriaId = 1,
            disponibilidad = true,
            imagen = "",
            descripcion = "Producto 1",
        ),
        ProductoEntity(
            productoId = 2,
            descripcion = "Producto 2",
            precio = 200.toBigDecimal(),
            nombre = "Pepsi",
            categoriaId = 1,
            disponibilidad = true,
            imagen = ""
        )
    )
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OfertaScreenPreview() {
    ProyectoFinalAplicada2Theme {
        OfertaBodyScreen(
            uiState = sampleUiState,
            onEvent = {},
            goToOfertaList = {},
            ofertaId = 0
        )
    }
}