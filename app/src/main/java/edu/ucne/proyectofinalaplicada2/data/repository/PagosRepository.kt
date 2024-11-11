package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.PagosDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.PagosEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.PagosRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PagosDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PagosRepository @Inject constructor(
    private val remoteDataSource: PagosRemoteDataSource,
    private val pagosDao: PagosDao
) {
    suspend fun addPago(pago: PagosDTO) = remoteDataSource.postPago(pago)
    suspend fun getPago(id: Int) = remoteDataSource.getPagoById(id)
    suspend fun deletePago(id: Int) = remoteDataSource.deletePago(id)
    suspend fun updatePago(id: Int, pago: PagosDTO) = remoteDataSource.putPago(id, pago)

    fun getPagos(): Flow<Resource<List<PagosEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val pagos = remoteDataSource.getPagos()

            pagos.forEach {
                pagosDao.save(
                    it.toPagosEntity()
                )
            }

            pagosDao.getAll().collect { pagosLocal ->
                emit(Resource.Success(pagosLocal))
            }
        }catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "\"Error HTTP GENERAL"))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Verificar conexion a internet"))

            pagosDao.getAll().collect { pagosLocal ->
                emit(Resource.Success(pagosLocal))
            }
        }
    }

}

private fun PagosDTO.toPagosEntity() = PagosEntity(
    pagoId = pagoId,
    pedidoId = pedidoId,
    tarjetaId = tarjetaId,
    fechaPago = fechaPago.toString(),
    monto = monto
)