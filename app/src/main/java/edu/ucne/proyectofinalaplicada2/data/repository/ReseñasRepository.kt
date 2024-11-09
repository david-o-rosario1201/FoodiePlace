package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.ReseñasDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReseñasEntity
import edu.ucne.proyectofinalaplicada2.data.remote.ReseñasRemoteSource
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.RemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReseñasDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ReseñasRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val reseñasDao: ReseñasDao
) {
    suspend fun addReseña(reseña: ReseñasDTO) = remoteDataSource.postReseña(reseña)
    suspend fun getReseña(id: Int) = remoteDataSource.getReseñaById(id)
    suspend fun deleteReseña(id: Int) = remoteDataSource.deleteReseña(id)
    suspend fun updateReseña(id: Int, reseña: ReseñasDTO) = remoteDataSource.putReseña(id, reseña)

    fun getReseñas(): Flow<Resource<List<ReseñasEntity>>> = flow {
        try{
            emit(Resource.Loading())
            val reseñas = remoteDataSource.getReseñas()

            reseñas.forEach {
                reseñasDao.save(
                    it.toReseñasEntity()
                )
            }

            reseñasDao.getAll().collect{reseñasLocal ->
                emit(Resource.Success(reseñasLocal))
            }

        }catch (e: HttpException){
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "Verificar conexion a internet"))

            reseñasDao.getAll().collect{reseñasLocal ->
                emit(Resource.Success(reseñasLocal))
            }
        }
    }
}

private fun ReseñasDTO.toReseñasEntity() = ReseñasEntity(
    resenaId = resenaId,
    usuarioId = usuarioId,
    comentario = comentario,
    fechaResena = fechaResena.toString(),
    calificacion = calificacion
)