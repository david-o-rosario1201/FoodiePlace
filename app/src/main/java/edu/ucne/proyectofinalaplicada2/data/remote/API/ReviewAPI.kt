package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReviewDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewAPI {

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Resenas")
    suspend fun getReview(): List<ReviewDTO>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Resenas/{id}")
    suspend fun getReviewById(@Path("id") id: Int): ReviewDTO

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Resenas")
    suspend fun postReview(@Body Review: ReviewDTO?): ReviewDTO?

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Resenas/{id}")
    suspend fun putReview(@Path("id") id: Int, @Body Review: ReviewDTO): ReviewDTO

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Resenas/{id}")
    suspend fun deleteReview(@Path("id") id: Int)

}