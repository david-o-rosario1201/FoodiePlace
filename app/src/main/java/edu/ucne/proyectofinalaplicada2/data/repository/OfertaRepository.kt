package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.OfertaDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.OfertaRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.OfertaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class OfertaRepository @Inject constructor(
    private val remoteDataSource: OfertaRemoteDataSource,
    private val ofertaDao: OfertaDao
) {
    suspend fun addOferta(ofertaDto: OfertaDto) = remoteDataSource.addOferta(ofertaDto)

    suspend fun getOferta(ofertaId: Int) = remoteDataSource.getOferta(ofertaId)

    suspend fun deleteOferta(ofertaId: Int) = remoteDataSource.deleteOferta(ofertaId)

    suspend fun updateOferta(ofertaId: Int, ofertaDto: OfertaDto) = remoteDataSource.updateOferta(ofertaId, ofertaDto)

    fun getOfertas(): Flow<Resource<List<OfertaEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val ofertas = remoteDataSource.getOfertas()

            ofertas.forEach {
                ofertaDao.addOferta(
                    it.toEntity()
                )
            }

            ofertaDao.getOfertas().collect{ ofertasLocal ->
                emit(Resource.Success(ofertasLocal))
            }
        } catch (e: HttpException){
            ofertaDao.getOfertas().collect{ ofertasLocal ->
                emit(Resource.Success(ofertasLocal))
            }

            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            ofertaDao.getOfertas().collect{ ofertasLocal ->
                emit(Resource.Success(ofertasLocal))
            }

            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}

private fun OfertaDto.toEntity() = OfertaEntity(
    ofertasId = ofertasId,
    productoId = productoId,
    descuento = descuento,
    precio = precio,
    precioOferta = precioOferta,
    fechaInicio = fechaInicio,
    fechaFinal = fechaFinal,
    imagen = imagen
)