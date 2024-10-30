package edu.ucne.proyectofinalaplicada2.presentation.algo

import edu.ucne.proyectofinalaplicada2.data.local.entities.AlgoEntity

data class AlgoUiState(
    val algoId: Int? = null,
    val nombre: String? = "",
    val tipo: String? = "",
    val algos: List<AlgoEntity> = emptyList(),
    val errorNombre: String? = "",
    val errorTipo: String? = "",
    val isSuccess: Boolean = false
)