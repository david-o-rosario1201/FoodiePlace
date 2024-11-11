package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.ReviewRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReviewDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewRemoteDataSource: ReviewRemoteDataSource,
    private val reseñasDao: ReviewDao
) {
    suspend fun addReseña(reseña: ReviewDTO) = reviewRemoteDataSource.postReseña(reseña)
    suspend fun getReseña(id: Int) = reviewRemoteDataSource.getReseñaById(id)
    suspend fun deleteReseña(id: Int) = reviewRemoteDataSource.deleteReseña(id)
    suspend fun updateReseña(id: Int, reseña: ReviewDTO) = reviewRemoteDataSource.putReseña(id, reseña)

    fun getReseñas(): Flow<Resource<List<ReviewEntity>>> = flow {
        try{
            emit(Resource.Loading())
            val reseñas = reviewRemoteDataSource.getReseñas()

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
            reseñasDao.getAll().collect{reseñasLocal ->
                emit(Resource.Success(reseñasLocal))
            }
            emit(Resource.Error(e.message ?: "Verificar conexion a internet"))


        }
    }
}

private fun ReviewDTO.toReseñasEntity() = ReviewEntity(
    resenaId = resenaId,
    usuarioId = usuarioId,
    comentario = comentario,
    fechaResena = fechaResena.toString(),
    calificacion = calificacion
)