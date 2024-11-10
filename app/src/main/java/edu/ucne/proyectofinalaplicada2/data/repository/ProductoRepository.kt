package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.ProductoDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.ProductoRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ProductoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoRemoteDataSource: ProductoRemoteDataSource,
    private val productoDao: ProductoDao
){
    suspend fun addProducto(productoDto: ProductoDto) = productoRemoteDataSource.addProducto(productoDto)

    suspend fun getProducto(productoId: Int) = productoRemoteDataSource.getProductoById(productoId)

    suspend fun deleteProducto(productoId: Int) = productoRemoteDataSource.deleteProducto(productoId)

    suspend fun updateProducto(productoId: Int, productoDto: ProductoDto) = productoRemoteDataSource.updateProducto(productoId, productoDto)

    fun getProductos(): Flow<Resource<List<ProductoEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val productos = productoRemoteDataSource.getProductos()

            productos.forEach {
                productoDao.addProducto(
                    it.toProductoEntity()
                )
            }

            productoDao.getProductos().collect { productosLocal ->
                emit(Resource.Success(productosLocal))
            }

        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))

            productoDao.getProductos().collect { productosLocal ->
                emit(Resource.Success(productosLocal))
            }
        }
    }

}


fun ProductoDto.toProductoEntity(): ProductoEntity {
    return ProductoEntity(
        productoId = this.productoId,
        nombre = this.nombre,
        categoriaId = this.categoriaId,
        descripcion = this.descripcion,
        precio = this.precio,
        disponibilidad = this.disponibilidad,
        imagen = this.imagen
    )
}