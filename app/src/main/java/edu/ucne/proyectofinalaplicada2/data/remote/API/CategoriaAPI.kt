package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.CategoriaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoriaAPI {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Categorias")
    suspend fun getCategorias(): List<CategoriaDto>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Categorias/{id}")
    suspend fun getCategoriaById(@Path("id") id: Int): CategoriaDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Categorias")
    suspend fun postCategoria(@Body categoria: CategoriaDto?): CategoriaDto?

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Categorias/{id}")
    suspend fun putCategoria(@Path("id") id: Int, @Body categoria: CategoriaDto): CategoriaDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Categorias/{id}")
    suspend fun deleteCategoria(@Path("id") id: Int)


}