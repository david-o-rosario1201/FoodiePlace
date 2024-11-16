package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.CategoriaDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.CategoriaRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CategoriaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val categoriaRemoteDataSource: CategoriaRemoteDataSource,
    private val categoriaDao: CategoriaDao
) {
    suspend fun addCategoria(categoria: CategoriaDto) = categoriaRemoteDataSource.postCategoria(categoria)
    suspend fun updateCategoria(id: Int, categoria: CategoriaDto) = categoriaRemoteDataSource.putCategoria(id, categoria)
    suspend fun deleteCategoria(id: Int) = categoriaRemoteDataSource.deleteCategoria(id)

    fun getCategorias(): Flow<Resource<List<CategoriaEntity>>> = flow {
        try{
            emit(Resource.Loading())
            val categorias = categoriaRemoteDataSource.getCategorias()

            categorias.forEach {
                categoriaDao.save(
                    it.toCategoriaEntity()
                )
            }

            categoriaDao.getAll().collect{categoriasLocal ->
                emit(Resource.Success(categoriasLocal))
            }

        }catch (e: HttpException){
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        }catch (e: Exception){
            categoriaDao.getAll().collect{categoriasLocal ->
                emit(Resource.Success(categoriasLocal))
            }
            emit(Resource.Error(e.message ?: "Verificar conexion a internet"))


        }
    }
}

private fun CategoriaDto.toCategoriaEntity() = CategoriaEntity(
    categoriaId = categoriaId,
    nombre = nombre,
    imagen = imagen ?: byteArrayOf()
)