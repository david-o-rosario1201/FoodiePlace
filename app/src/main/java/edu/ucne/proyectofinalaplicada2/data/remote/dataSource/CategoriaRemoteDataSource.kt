package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.CategoriaAPI
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CategoriaDto
import javax.inject.Inject

class CategoriaRemoteDataSource @Inject constructor(
    private val categoriaApi: CategoriaAPI
) {
    suspend fun getCategorias() = categoriaApi.getCategorias()
    suspend fun getCategoriaById(id: Int) = categoriaApi.getCategoriaById(id)
    suspend fun postCategoria(categoria: CategoriaDto) = categoriaApi.postCategoria(categoria)
    suspend fun putCategoria(id: Int, categoria: CategoriaDto) = categoriaApi.putCategoria(id, categoria)
    suspend fun deleteCategoria(id: Int) = categoriaApi.deleteCategoria(id)
}