package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface CarritoApi {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/carrito")
    suspend fun getCarrito(): List<CarritoDto>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/carrito/{id}")
    suspend fun getCarritoById(@Path("id") id: Int): CarritoDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/carrito")
    suspend fun postCarrito(@Body carrito: CarritoDto?): CarritoDto?

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/carrito/{id}")
    suspend fun deleteCarrito(@Path("id") id: Int)


}