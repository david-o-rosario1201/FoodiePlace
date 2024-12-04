package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.PedidoDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.PedidoRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PedidoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PedidoRepository @Inject constructor(
    private val pedidoRemoteDataSource: PedidoRemoteDataSource,
    private val pedidoDao: PedidoDao
) {
    suspend fun addPedido(pedidoDto: PedidoDto) = pedidoRemoteDataSource.addPedido(pedidoDto)

    suspend fun getPedido(pedidoId: Int) = pedidoRemoteDataSource.getPedido(pedidoId)

    suspend fun deletePedido(pedidoId: Int) = pedidoRemoteDataSource.deletePedido(pedidoId)

    suspend fun updatePedido(pedidoId: Int, pedidoDto: PedidoDto) = pedidoRemoteDataSource.updatePedido(pedidoId,pedidoDto)

    fun getPedidos(): Flow<Resource<List<PedidoEntity>>> = flow{
        try {
            emit(Resource.Loading())
            val pedidos = pedidoRemoteDataSource.getPedidos()

            pedidos.forEach {
                pedidoDao.addPedido(
                    it.toEntity()
                )
            }

            pedidoDao.getPedidos().collect{ pedidosLocal ->
                emit(Resource.Success(pedidosLocal))
            }

        } catch (e: HttpException){
            pedidoDao.getPedidos().collect{ pedidosLocal ->
                emit(Resource.Success(pedidosLocal))
            }

            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            pedidoDao.getPedidos().collect{ pedidosLocal ->
                emit(Resource.Success(pedidosLocal))
            }

            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}

private fun PedidoDto.toEntity() = PedidoEntity(
    pedidoId = pedidoId,
    tiempo = tiempo,
    usuarioId = usuarioId,
    fechaPedido = fechaPedido,
    total = total,
    paraLlevar = paraLlevar,
    estado = estado,
    pedidoDetalle = pedidoDetalle
)