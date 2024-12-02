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

    fun getTarjeta(usuarioId: Int) : Flow<List<TarjetaEntity>> {
        return tarjetaDao.getTarjetasPorUsuario(usuarioId)
    }


    suspend fun deleteTarjeta(tarjetaId: Int) {
        tarjetaRemoteDataSource.deleteTarjeta(tarjetaId)
    }

    suspend fun updateTarjeta(tarjetaId: Int, tarjeta: TarjetaDto) =tarjetaRemoteDataSource.updateTarjeta(tarjetaId,tarjeta)

    fun getTarjetas(): Flow<Resource<List<TarjetaEntity>>> = flow {
        try {
            emit(Resource.Loading())
            // Obtener tarjetas desde el servidor
            val tarjetas = tarjetaRemoteDataSource.getTarjetas()

            // Guardar las tarjetas en la base de datos local
            tarjetas.forEach {
                tarjetaDao.addTarjeta(it.toTarjetaEntity())
            }

            // Emitir las tarjetas locales como un flujo continuo
            tarjetaDao.getTarjetas().collect { tarjetasLocal ->
                emit(Resource.Success(tarjetasLocal))
            }

        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) { // Emitir tarjetas locales en caso de error
            tarjetaDao.getTarjetas().collect { tarjetasLocal ->
                emit(Resource.Success(tarjetasLocal))
            }
            emit(Resource.Error("Error desconocido ${e.message}"))


        }
    }

    fun getTarjetasPorUsuario(usuarioId: Int): Flow<Resource<List<TarjetaEntity>>> = flow {
        try {
            emit(Resource.Loading()) // Emite un estado de carga

            tarjetaDao.getTarjetasPorUsuario(usuarioId).collect { tarjetasLocal ->
                emit(Resource.Success(tarjetasLocal))
            }

        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener tarjetas: ${e.message}")) // Emite error en caso de excepción
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
        cvv = this.cvv,
        nombreTitular = this.nombreTitular
    )
}
