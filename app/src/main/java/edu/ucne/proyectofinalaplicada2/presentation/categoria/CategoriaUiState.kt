package edu.ucne.proyectofinalaplicada2.presentation.categoria

import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity

data class CategoriaUiState (
    val categoriaId: Int? = null,
    val nombre: String = "",
    val imagen: String = "",
    val nombreError: String? = null,
    val imagenError: String? = null,
    val errorMessge: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val categorias: List<CategoriaEntity> = emptyList(),
    val isRefreshing: Boolean = false
)
