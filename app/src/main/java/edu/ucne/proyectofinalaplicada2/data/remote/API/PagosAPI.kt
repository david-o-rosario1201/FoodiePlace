package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.PagosDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface PagosAPI {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Pagos")
    suspend fun getPagos(): List<PagosDTO>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Pagos/{id}")
    suspend fun getPagoById(@Path("id") id: Int): PagosDTO

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Pagos")
    suspend fun postPago(@Body pago: PagosDTO?): PagosDTO?

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Pagos/{id}")
    suspend fun putPago(@Path("id") id: Int, @Body pago: PagosDTO): PagosDTO

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Pagos/{id}")
    suspend fun deletePago(@Path("id") id: Int)

}