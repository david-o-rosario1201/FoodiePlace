package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ProductoDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProductoApi {
    @GET("api/Productos")
    suspend fun getProductos(): List<ProductoDto>

    @GET("api/Productos/{id}")
    suspend fun getProductoById(id: Int): ProductoDto

    @POST("api/Productos")
    suspend fun addProducto(producto: ProductoDto): ProductoDto

    @PUT("api/Productos/{id}")
    suspend fun updateProducto(id: Int, producto: ProductoDto): ProductoDto

    @DELETE("api/Productos/{id}")
    suspend fun deleteProducto(id: Int)



}