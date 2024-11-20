package edu.ucne.proyectofinalaplicada2.presentation.pedido

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.navigation.BottomBarNavigation
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme
import java.math.BigDecimal
import java.time.Instant
import java.util.Date

@Composable
fun PedidoListScreen(
    viewModel: PedidoViewModel = hiltViewModel(),
    onClickPedido: (Int) -> Unit,
    onClickNotifications: () -> Unit,
    navHostController: NavHostController,
    onDrawer: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PedidoListBodyScreen(
        uiState = uiState,
        onClickPedido = onClickPedido,
        onClickNotifications = onClickNotifications,
        navHostController = navHostController,
        onDrawer = onDrawer
    )
}

@Composable
private fun PedidoListBodyScreen(
    uiState: PedidoUiState,
    onClickPedido: (Int) -> Unit,
    onClickNotifications: () -> Unit,
    navHostController: NavHostController,
    onDrawer: () -> Unit
){
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Pedidos",
                onClickMenu = onDrawer,
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        },
        bottomBar = {
            BottomBarNavigation(navController = navHostController)
        }
    ){ innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){
            if(uiState.pedidos.isEmpty()){
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
                    items(uiState.pedidos){
                        PedidoRow(
                            it = it,
                            onClickPedido = onClickPedido
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PedidoRow(
    it: PedidoEntity,
    onClickPedido: (Int) -> Unit
){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onClickPedido(it.pedidoId ?: 0) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.ordenes),
                contentDescription = "Pedidos",
                modifier = Modifier.size(80.dp).padding(end = 30.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ){
                Row {
                    Text(
                        text = "Pedido",
                        color = Color.Black
                    )
                    Text(
                        text = it.pedidoId.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Row {
                    Text(
                        text = "Total",
                        color = Color.Black
                    )
                    Text(
                        text = "$.${it.total}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Row {
                    Text(
                        text = "Cantidad",
                        color = Color.Black
                    )
                    Text(
                        text = it.pedidoDetalle.size.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            Text(
                text = it.estado,
                color = Color.Black
            )
        }
    }
}

private val sampleUiState = PedidoUiState(
    pedidos = listOf(
        PedidoEntity(
            pedidoId = 1,
            usuarioId = 1,
            fechaPedido = Date.from(Instant.now()),
            total = BigDecimal.valueOf(100),
            paraLlevar = true,
            estado = "Pendiente",
            pedidoDetalle = emptyList()
        ),
        PedidoEntity(
            pedidoId = 2,
            usuarioId = 2,
            fechaPedido = Date.from(Instant.now()),
            total = BigDecimal.valueOf(150),
            paraLlevar = false,
            estado = "Entregado",
            pedidoDetalle = emptyList()
        )
    )
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PedidoListScreenPreview(){
    ProyectoFinalAplicada2Theme {
        PedidoListBodyScreen(
            uiState = sampleUiState,
            onClickPedido = {},
            onClickNotifications = {},
            navHostController = rememberNavController(),
            onDrawer = {}
        )
    }
}