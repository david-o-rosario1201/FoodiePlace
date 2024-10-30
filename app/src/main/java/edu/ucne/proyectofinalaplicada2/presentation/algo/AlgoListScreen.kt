package edu.ucne.proyectofinalaplicada2.presentation.algo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.data.local.entities.AlgoEntity
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent

@Composable
fun AlgoListScreen(
    viewModel: AlgoViewModel = hiltViewModel(),
    onClickAlgo: (Int) -> Unit,
    onAddAlgo: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AlgoListBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onClickAlgo = onClickAlgo,
        onAddAlgo = onAddAlgo
    )
}

@Composable
private fun AlgoListBodyScreen(
    uiState: AlgoUiState,
    onEvent: (AlgoUiEvent) -> Unit,
    onClickAlgo: (Int) -> Unit,
    onAddAlgo: () -> Unit
){
    val algos = remember { mutableStateListOf(*uiState.algos.toTypedArray()) }

    LaunchedEffect(uiState.algos) {
        algos.clear()
        algos.addAll(uiState.algos)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Lista de Algos",
                onClickMenu = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddAlgo
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Agregar Nuevo Algo"
                )
            }
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if(uiState.algos.isEmpty()){
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "Lista vacía",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                else{
                    items(uiState.algos){

                    }
                }
            }
        }
    }
}

@Composable
private fun AlgoRow(
    it: AlgoEntity,
    onClickAlgo: (Int) -> Unit
){
    Card(
        onClick = {
            onClickAlgo(it.AlgoId ?: 0)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB0BEC5)
        ),
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .heightIn(min = 160.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Id: ${it.AlgoId}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Nombre: ${it.Nombre}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Tipo: ${it.Tipo}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}