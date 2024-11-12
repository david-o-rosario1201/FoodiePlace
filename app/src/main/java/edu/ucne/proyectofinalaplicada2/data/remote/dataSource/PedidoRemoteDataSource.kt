package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.PedidoApi
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PedidoDto
import javax.inject.Inject

class PedidoRemoteDataSource @Inject constructor(
    private val pedidoApi: PedidoApi
) {
    suspend fun addPedido(pedidoDto: PedidoDto) = pedidoApi.addPedido(pedidoDto)

    suspend fun getPedido(pedidoId: Int) = pedidoApi.getPedido(pedidoId)

    suspend fun deletePedido(pedidoId: Int) = pedidoApi.deletePedido(pedidoId)

    suspend fun updatePedido(pedidoId: Int, pedidoDto: PedidoDto) = pedidoApi.updatePedido(pedidoId,pedidoDto)

    suspend fun getPedidos() = pedidoApi.getPedidos()
}