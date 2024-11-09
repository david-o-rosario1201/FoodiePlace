package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReseñasDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ReseñasAPI {

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Reseñas")
    suspend fun getReseñas(): List<ReseñasDTO>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Reseñas/{id}")
    suspend fun getReseñaById(@Path("id") id: Int): ReseñasDTO

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Reseñas")
    suspend fun postReseña(@Body reseña: ReseñasDTO?): ReseñasDTO?

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Reseñas/{id}")
    suspend fun putReseña(@Path("id") id: Int, @Body reseña: ReseñasDTO): ReseñasDTO

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Reseñas/{id}")
    suspend fun deleteReseña(@Path("id") id: Int)

}