package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.ReservacionesDao
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReservacionesDto
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.ReservacionesRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.Resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ReservacionesRepository @Inject constructor(
    private val reservacionesRemoteDataSource: ReservacionesRemoteDataSource,
    private val reservacionesDao: ReservacionesDao
) {

    private var reservacionesCargadas: List<ReservacionesEntity> = emptyList()
    suspend fun addReservacion(reservacionesDto: ReservacionesDto) = reservacionesRemoteDataSource.addReservacion(reservacionesDto)

    suspend fun getReservacion(reservacionId: Int) = reservacionesRemoteDataSource.getReservacionById(reservacionId)

    suspend fun deleteReservacion(reservacionId: Int) = reservacionesRemoteDataSource.deleteReservacion(reservacionId)

    suspend fun updateReservacion(reservacionId: Int, reservacionesDto: ReservacionesDto) = reservacionesRemoteDataSource.updateReservacion(reservacionId, reservacionesDto)

    fun getReservaciones(): Flow<Resource<List<ReservacionesEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val reservaciones = reservacionesRemoteDataSource.getReservaciones()

            reservaciones.forEach {
                reservacionesDao.save(
                    it.toReservacionesEntity()
                )
            }

            reservacionesDao.getAll().collect { reservacionesLocal ->
                emit(Resource.Success(reservacionesLocal))
            }

        } catch (e: HttpException) {
            reservacionesDao.getAll().collect { reservacionesLocal ->
                emit(Resource.Success(reservacionesLocal))
            }
            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception) {
            reservacionesDao.getAll().collect { reservacionesLocal ->
                emit(Resource.Success(reservacionesLocal))
            }
            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }

    fun searchReservaciones(query: String): List<ReservacionesEntity> {
        val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return reservacionesCargadas.filter { reservaciones ->
            reservaciones.fechaReservacion?.let { date ->
                dateFormat.format(date).contains(query, ignoreCase = true)
            } == true
        }
    }

}

fun ReservacionesDto.toReservacionesEntity(): ReservacionesEntity {
    return ReservacionesEntity(
        reservacionId = this.reservacionId,
        usuarioId = this.usuarioId,
        fechaReservacion = this.fechaReservacion,
        numeroPersonas = this.numeroPersonas,
        estado = this.estado,
        numeroMesa = this.numeroMesa,
        horaReservacion = this.horaReservacion
    )
}
