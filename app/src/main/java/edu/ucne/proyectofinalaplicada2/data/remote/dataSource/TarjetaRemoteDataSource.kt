package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.TarjetaApi
import edu.ucne.proyectofinalaplicada2.data.remote.dto.TarjetaDto
import javax.inject.Inject

class TarjetaRemoteDataSource @Inject constructor(
    private val tarjetaApi: TarjetaApi
) {
    suspend fun addTarjeta(tarjetaDto: TarjetaDto) = tarjetaApi.addTarjeta(tarjetaDto)

    suspend fun getTarjeta(tarjetaId: Int) = tarjetaApi.getTarjeta(tarjetaId)

    suspend fun deleteTarjeta(tarjetaId: Int) = tarjetaApi.deleteTarjeta(tarjetaId)

    suspend fun updateTarjeta(tarjetaId: Int, tarjetaDto: TarjetaDto) = tarjetaApi.updateTarjeta(tarjetaId, tarjetaDto)

    suspend fun getTarjetas() = tarjetaApi.getTarjetas()
}
