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
    private val detalleCarritoDao: CarritoDetalleDao
) {
    suspend fun getCarritoById(id: Int) = carritoDao.getCarritoById(id)
    suspend fun addCarritoApi(carrito: CarritoDto) = carritoRemoteDataSource.postCarrito(carrito)
    suspend fun saveCarrito(carrito: CarritoEntity) = carritoDao.save(carrito)
    suspend fun deleteCarrito(id: Int) = carritoRemoteDataSource.deleteCarrito(id)
    suspend fun getLastCarrito() = carritoDao.getLastCarrito()

    fun getCarritoss(): Flow<Resource<List<CarritoEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val carrito = carritoRemoteDataSource.getCarrito()

            carrito.forEach {
                carritoDao.save(
                    it.toCarritoEntity()
                )
            }

            carritoDao.getAll().collect { carritoLocal ->
                emit(Resource.Success(carritoLocal))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Verificar conexion a internet"))

            carritoDao.getAll().collect { carritoLocal ->
                emit(Resource.Success(carritoLocal))
            }
        }
    }

    fun getCarritosPorUsuario(usuarioId: Int): Flow<Resource<List<CarritoEntity>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val carritos = carritoDao.getCarritosPorUsuario(usuarioId)
                emit(Resource.Success(carritos))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Error al obtener carritos"))
            }
        }
    }

    fun getCarritoDetallesPorCarritoId(carritoId: Int): Flow<List<CarritoDetalleEntity>> {
        return carritoDao.getCarritoDetallesPorCarritoId(carritoId)
    }

    suspend fun addCarritoDetalle(detalle: CarritoDetalleEntity) {
        detalleCarritoDao.save(detalle)
    }

    suspend fun getLastCarritoByPersona(personaId: Int) =
        carritoDao.getLastCarritoByUsuario(personaId)

    suspend fun CarritoExiste(productoId: Int, carritoId: Int) {
        detalleCarritoDao.carritoDetalleExit(productoId, carritoId)
    }

    suspend fun getCarritoDetalleByProductoId(productoId: Int, carritoId: Int) =
        detalleCarritoDao.getCarritoDetalleByProductoId(productoId, carritoId)

}
private fun CarritoDto.toCarritoEntity() = CarritoEntity(
    carritoId = carritoId,
    usuarioId = usuarioId,
    fechaCreacion = fechaCreacion,
    pagado, carritoDetalle.toMutableList()
)