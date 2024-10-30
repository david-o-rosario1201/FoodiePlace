package edu.ucne.proyectofinalaplicada2.presentation.algo

data class AlgoUiState(
    val algoId: Int? = null,
    val nombre: String = "",
    val tipo: String = "",
    //val algos: List<Algo> = emptyList()
    val errorNombre: String = "",
    val errorTipo: String = "",
    val isSuccess: Boolean = false
)