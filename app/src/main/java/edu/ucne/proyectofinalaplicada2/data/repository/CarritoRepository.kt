package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.CarritoDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.CarritoRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CarritoRepository @Inject constructor(
    private val carritoRemoteDataSource: CarritoRemoteDataSource,
    private val carritoDao: CarritoDao
) {
    suspend fun getCarrito() = carritoRemoteDataSource.getCarrito()
    suspend fun getCarritoById(id: Int) = carritoRemoteDataSource.getCarritoById(id)
    suspend fun addCarrito(carrito: CarritoDto) = carritoRemoteDataSource.postCarrito(carrito)
    suspend fun deleteCarrito(id: Int) = carritoRemoteDataSource.deleteCarrito(id)

    fun getCarritoss(): Flow<Resource<List<CarritoEntity>>> = flow {
        try{emit(Resource.Loading())
            val carrito = carritoRemoteDataSource.getCarrito()

            carritoDao.getAll().collect{carritoLocal ->
                emit(Resource.Success(carritoLocal))
            }

        }catch (e: HttpException){
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        }catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Verificar conexion a internet"))

            carritoDao.getAll().collect { carritoLocal ->
                emit(Resource.Success(carritoLocal))
            }
        }
    }
}