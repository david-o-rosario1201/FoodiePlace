package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.PagosAPI
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PagosDTO
import javax.inject.Inject

class PagosRemoteDataSource @Inject constructor(
    private val PagosAPI: PagosAPI
) {
    suspend fun postPago(pago: PagosDTO) = PagosAPI.postPago(pago)
    suspend fun getPagoById(id: Int) = PagosAPI.getPagoById(id)
    suspend fun deletePago(id: Int) = PagosAPI.deletePago(id)
    suspend fun putPago(id: Int, pago: PagosDTO) = PagosAPI.putPago(id, pago)
    suspend fun getPagos() = PagosAPI.getPagos()

}