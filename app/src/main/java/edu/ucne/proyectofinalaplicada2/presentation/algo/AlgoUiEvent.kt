package edu.ucne.proyectofinalaplicada2.presentation.algo

sealed interface AlgoUiEvent {
    data class AlgoIdChanged(val algoId: Int?): AlgoUiEvent
    data class NombreChanged(val nombre: String): AlgoUiEvent
    data class TipoChanged(val tipo: String): AlgoUiEvent
    data class SelectedAlgo(val algoId: Int): AlgoUiEvent
    data object Save: AlgoUiEvent
    data object Delete: AlgoUiEvent
}