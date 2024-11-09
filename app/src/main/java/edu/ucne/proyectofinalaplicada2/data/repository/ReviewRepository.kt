package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.ReseñasDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReseñasEntity
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
    private val reviewDao: ReviewDao
) {
    suspend fun addReview(review: ReviewDTO) = reviewRemoteDataSource.postReview(review)
    suspend fun getReview(id: Int) = reviewRemoteDataSource.getReviewById(id)
    suspend fun deleteReview(id: Int) = reviewRemoteDataSource.deleteReview(id)
    suspend fun updateReview(id: Int, review: ReviewDTO) = reviewRemoteDataSource.putReview(id, review)

    fun getReseñas(): Flow<Resource<List<ReviewEntity>>> = flow {
        try{
            emit(Resource.Loading())
            val reseñas = reviewRemoteDataSource.getReview()

            reseñas.forEach {
                reviewDao.save(
                    it.toreviewEntity()
                )
            }

            reviewDao.getAll().collect{ reviewLocal ->
                emit(Resource.Success(reviewLocal))
            }

        }catch (e: HttpException){
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        }catch (e: Exception){
            reviewDao.getAll().collect{ reviewLocal ->
                emit(Resource.Success(reviewLocal))
            }
            emit(Resource.Error(e.message ?: "Verificar conexion a internet"))


        }
    }
}

private fun ReviewDTO.toreviewEntity() = ReviewEntity(
    resenaId = resenaId,
    usuarioId = usuarioId,
    comentario = comentario,
    fechaResena = fechaResena.toString(),
    calificacion = calificacion
)