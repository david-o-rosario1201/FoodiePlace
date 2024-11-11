package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.ReviewAPI
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReviewDTO
import javax.inject.Inject

class ReviewRemoteDataSource @Inject constructor(
    private val ReviewAPI: ReviewAPI
) {
    suspend fun postReview(review: ReviewDTO) = ReviewAPI.postReview(review)
    suspend fun getReviewById(id: Int) = ReviewAPI.getReviewById(id)
    suspend fun deleteReview(id: Int) = ReviewAPI.deleteReview(id)
    suspend fun putReview(id: Int, review: ReviewDTO) = ReviewAPI.putReview(id, review)
    suspend fun getReview() = ReviewAPI.getReview()

}