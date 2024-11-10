package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.TarjetaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TarjetaApi {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Tarjetas")
    suspend fun getTarjetas(): List<TarjetaDto>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Tarjetas/{id}")
    suspend fun getTarjetaById(@Path("id") id: Int): TarjetaDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Tarjetas")
    suspend fun addTarjeta(@Body tarjeta: TarjetaDto?): TarjetaDto?

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @PUT("api/Tarjetas/{id}")
    suspend fun updateTarjeta(@Path("id") id: Int, @Body tarjeta: TarjetaDto): TarjetaDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Tarjetas/{id}")
    suspend fun deleteTarjeta(@Path("id") id: Int)
}
