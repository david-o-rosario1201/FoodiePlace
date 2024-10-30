package edu.ucne.proyectofinalaplicada2.presentation.algo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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
    //val algos = remember { mutableStateListOf() }
}