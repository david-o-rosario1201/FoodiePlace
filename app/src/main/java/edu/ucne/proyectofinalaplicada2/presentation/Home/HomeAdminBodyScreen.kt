package edu.ucne.proyectofinalaplicada2.presentation.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import edu.ucne.proyectofinalaplicada2.R
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.presentation.navigation.Screen
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun HomeAdminBodyScreen(
    uiState: HomeUiState,
    navController: NavHostController,
    onDrawer: () -> Unit,
    onClickNotifications: () -> Unit
){
    val items = listOf(
        HomeAdminItem(
            title = "Pedidos",
            image = painterResource(id = R.drawable.pedido_icon),
            onClick = { navController.navigate(Screen.PedidoListScreen) }
        ),
        HomeAdminItem(
            title = "Categorías",
            image = painterResource(id = R.drawable.categoria_icon),
            onClick = { navController.navigate(Screen.CategoriaListScreen) }
        ),
        HomeAdminItem(
            title = "Productos",
            image = painterResource(id = R.drawable.producto_icon),
            onClick = { navController.navigate(Screen.ProductoListScreen) }
        ),
        HomeAdminItem(
            title = "Ofertas",
            image = painterResource(id = R.drawable.oferta_icon),
            onClick = { navController.navigate(Screen.OfertaListScreen)}
        ),
        HomeAdminItem(
            title = "Reviews",
            image = painterResource(id = R.drawable.resena_icon),
            onClick = { navController.navigate(Screen.ReviewListScreen) }
        )
    )
    Scaffold(
        topBar = {
            TopBarComponent(
                title = " ",
                onClickMenu = onDrawer,
                onClickNotifications = onClickNotifications,
                notificationCount = 0
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
        ){
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hola, ${uiState.usuarioNombre}",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight(700),
                    )
                )
                if (!uiState.fotoPerfil.isNullOrBlank()) {
                    AsyncImage(
                        model = uiState.fotoPerfil,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            HorizontalDivider()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items.size) { index ->
                    HomeAdminCard(
                        title = items[index].title,
                        image = items[index].image,
                        onClick = items[index].onClick
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeAdminCard(
    title: String,
    image: Painter,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.8f)
            .clickable { onClick() }
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
        }
    }
}

data class HomeAdminItem(
    val title: String,
    val image: Painter,
    val onClick: () -> Unit
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeAdminBodyScreenPreview() {
    ProyectoFinalAplicada2Theme {
        HomeAdminBodyScreen(
            uiState = HomeUiState(),
            navController = NavHostController(LocalContext.current),
            onDrawer = {},
            onClickNotifications = {}
        )
    }
}