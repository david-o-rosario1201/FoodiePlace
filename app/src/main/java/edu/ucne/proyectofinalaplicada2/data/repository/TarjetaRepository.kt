package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.TarjetaDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.TarjetaEntity
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.TarjetaRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.TarjetaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class TarjetaRepository @Inject constructor(
    private val tarjetaRemoteDataSource: TarjetaRemoteDataSource,
    private val tarjetaDao: TarjetaDao
) {
    suspend fun addTarjeta(tarjetaDto: TarjetaDto) = tarjetaRemoteDataSource.addTarjeta(tarjetaDto)

    suspend fun getTarjeta(tarjetaId: Int) = tarjetaRemoteDataSource.getTarjeta(tarjetaId)

    suspend fun deleteTarjeta(tarjetaId: Int) = tarjetaRemoteDataSource.deleteTarjeta(tarjetaId)

    suspend fun updateTarjeta(tarjetaId: Int, tarjetaDto: TarjetaDto) = tarjetaRemoteDataSource.updateTarjeta(tarjetaId, tarjetaDto)

    fun getTarjetas(): Flow<Resource<List<TarjetaEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val tarjetas = tarjetaRemoteDataSource.getTarjetas()

            tarjetas.forEach {
                tarjetaDao.addTarjeta(
                    it.toTarjetaEntity()
                )
            }

            tarjetaDao.getTarjetas().collect { tarjetasLocal ->
                emit(Resource.Success(tarjetasLocal))
            }

        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))

            tarjetaDao.getTarjetas().collect { tarjetasLocal ->
                emit(Resource.Success(tarjetasLocal))
            }
        }
    }
}

fun TarjetaDto.toTarjetaEntity(): TarjetaEntity {
    return TarjetaEntity(
        tarjetaId = this.tarjetaId ?: 0,
        usuarioId = this.usuarioId,
        tipoTarjeta = this.tipoTarjeta,
        numeroTarjeta = this.numeroTarjeta,
        fechaExpiracion = this.fechaExpiracion,
        cvv = this.cvv
    )
}
