package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReservacionesDto

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservacionesAPI {

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Reservaciones")
    suspend fun getReservaciones(): List<ReservacionesDto>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Reservaciones/{id}")
    suspend fun getReservacionById(@Path("id") id: Int): ReservacionesDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Reservaciones")
    suspend fun postReservacion(@Body reservacion: ReservacionesDto?): ReservacionesDto?

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Reservaciones/{id}")
    suspend fun putReservacion(@Path("id") id: Int, @Body reservacion: ReservacionesDto): ReservacionesDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Reservaciones/{id}")
    suspend fun deleteReservacion(@Path("id") id: Int)
}
