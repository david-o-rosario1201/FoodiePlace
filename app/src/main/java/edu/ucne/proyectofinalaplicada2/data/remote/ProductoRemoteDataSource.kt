package edu.ucne.proyectofinalaplicada2.data.remote

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ProductoDto
import javax.inject.Inject

class ProductoRemoteDataSource @Inject constructor(
    private val productoApi: ProductoApi

){
    suspend fun getProductos()= productoApi.getProductos()

    suspend fun getProductoById(id: Int) = productoApi.getProductoById(id)

    suspend fun addProducto(producto: ProductoDto) = productoApi.addProducto(producto)

    suspend fun updateProducto(id: Int, producto: ProductoDto) = productoApi.updateProducto(id, producto)

    suspend fun deleteProducto(id: Int) = productoApi.deleteProducto(id)



}