package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.OfertaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OfertaApi {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Ofertas")
    suspend fun addOferta(@Body ofertaDto: OfertaDto): OfertaDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Ofertas/{ofertaId}")
    suspend fun getOferta(@Path("ofertaId") ofertaId: Int): OfertaDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Ofertas/{ofertaId}")
    suspend fun deleteOferta(@Path("ofertaId") ofertaId: Int): Response<Unit>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @PUT("api/Ofertas/{ofertaId}")
    suspend fun updateOferta(
        @Path("ofertaId") ofertaId: Int,
        @Body oferta: OfertaDto
    ): Response<OfertaDto>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Ofertas")
    suspend fun getOfertas(): List<OfertaDto>
}