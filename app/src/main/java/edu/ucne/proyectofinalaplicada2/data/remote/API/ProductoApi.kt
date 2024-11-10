package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ProductoDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProductoApi {

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Productos")
    suspend fun getProductos(): List<ProductoDto>


    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Productos/{id}")
    suspend fun getProductoById(id: Int): ProductoDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Productos")
    suspend fun addProducto(producto: ProductoDto): ProductoDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @PUT("api/Productos/{id}")
    suspend fun updateProducto(id: Int, producto: ProductoDto): ProductoDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Productos/{id}")
    suspend fun deleteProducto(id: Int)



}