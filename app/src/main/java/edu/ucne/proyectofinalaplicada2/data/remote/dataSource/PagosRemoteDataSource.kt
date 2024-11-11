package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.PagosAPI
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PagosDTO
import javax.inject.Inject

class PagosRemoteDataSource @Inject constructor(
    private val pagosAPI: PagosAPI
) {
    suspend fun postPago(pago: PagosDTO) = pagosAPI.postPago(pago)
    suspend fun getPagoById(id: Int) = pagosAPI.getPagoById(id)
    suspend fun deletePago(id: Int) = pagosAPI.deletePago(id)
    suspend fun putPago(id: Int, pago: PagosDTO) = pagosAPI.putPago(id, pago)
    suspend fun getPagos() = pagosAPI.getPagos()

}