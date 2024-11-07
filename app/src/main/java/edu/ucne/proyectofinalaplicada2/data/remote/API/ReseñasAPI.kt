package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReseñasDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReseñasAPI {
    @GET("api/Reseñas")
    suspend fun getReseñas(): List<ReseñasDTO>
    @GET("api/Reseñas/{id}")
    suspend fun getReseñaById(@Path("id") id: Int): ReseñasDTO
    @POST("api/Reseñas")
    suspend fun postReseña(@Body reseña: ReseñasDTO?): ReseñasDTO?
    @POST("api/Reseñas/{id}")
    suspend fun putReseña(@Path("id") id: Int, @Body reseña: ReseñasDTO): ReseñasDTO
    @DELETE("api/Reseñas/{id}")
    suspend fun deleteReseña(@Path("id") id: Int)

}