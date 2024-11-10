package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.OfertaApi
import edu.ucne.proyectofinalaplicada2.data.remote.dto.OfertaDto
import javax.inject.Inject

class OfertaRemoteDataSource @Inject constructor(
    private val ofertaApi: OfertaApi
) {
    suspend fun addOferta(ofertaDto: OfertaDto) = ofertaApi.addOferta(ofertaDto)

    suspend fun getOferta(ofertaId: Int) = ofertaApi.getOferta(ofertaId)

    suspend fun deleteOferta(ofertaId: Int) = ofertaApi.deleteOferta(ofertaId)

    suspend fun updateOferta(ofertaId: Int, ofertaDto: OfertaDto) = ofertaApi.updateOferta(ofertaId,ofertaDto)

    suspend fun getOfertas() = ofertaApi.getOfertas()
}