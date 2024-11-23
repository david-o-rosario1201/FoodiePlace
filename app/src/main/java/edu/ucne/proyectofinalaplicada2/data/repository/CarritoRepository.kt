package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.CarritoDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.CarritoDetalleDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
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
    private val carritoDao: CarritoDao,
    private val DetalleCarritoDao: CarritoDetalleDao
) {
    suspend fun getCarrito() = carritoRemoteDataSource.getCarrito()
    suspend fun getCarritoById(id: Int) = carritoDao.getCarritoById(id)
    suspend fun addCarrito(carrito: CarritoDto) = carritoRemoteDataSource.postCarrito(carrito)
    suspend fun deleteCarrito(id: Int) = carritoRemoteDataSource.deleteCarrito(id)

    fun getCarritoss(): Flow<Resource<List<CarritoEntity>>> = flow {
        try{emit(Resource.Loading())
            val carrito = carritoRemoteDataSource.getCarrito()

            carrito.forEach {
                carritoDao.save(
                    it.toCarritoEntity()
                )
            }

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

    suspend fun addCarritoDetalle(detalle: CarritoDetalleEntity) {
        DetalleCarritoDao.save(detalle)
    }

    fun getCarritoDetalles(carritoId: Int): Flow<List<CarritoDetalleEntity>> {
        return DetalleCarritoDao.getCarritoDetalles(carritoId)
    }

    suspend fun clearCarrito(carritoId: Int) {
        DetalleCarritoDao.clearCarrito(carritoId)
    }
}
private fun CarritoDto.toCarritoEntity() = CarritoEntity(
    carritoId = carritoId,
    usuarioId = usuarioId,
    fechaCreacion = fechaCreacion,
    pagado, carritoDetalle.toMutableList()
)