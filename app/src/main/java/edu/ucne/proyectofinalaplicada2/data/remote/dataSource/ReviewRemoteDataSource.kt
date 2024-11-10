package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.ReviewAPI
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReseñasDTO
import javax.inject.Inject

class ReviewRemoteDataSource @Inject constructor(
    private val ReviewAPI: ReviewAPI
) {
    suspend fun postReseña(reseña: ReseñasDTO) = ReviewAPI.postReseña(reseña)
    suspend fun getReseñaById(id: Int) = ReviewAPI.getReseñaById(id)
    suspend fun deleteReseña(id: Int) = ReviewAPI.deleteReseña(id)
    suspend fun putReseña(id: Int, reseña: ReseñasDTO) = ReviewAPI.putReseña(id, reseña)
    suspend fun getReseñas() = ReviewAPI.getReseñas()

}