package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.NotificacionDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.NotificacionEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.NotificacionRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.NotificacionDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class NotificacionRepository @Inject constructor(
    private val notificacionRemoteDataSource: NotificacionRemoteDataSource,
    private val notificacionDao: NotificacionDao
) {
    suspend fun addNotificacion(notificacionDto: NotificacionDto) = notificacionRemoteDataSource.addNotificacion(notificacionDto)

    suspend fun deleteNotificacion(notificacionId: Int) = notificacionRemoteDataSource.deleteNotificacion(notificacionId)

    fun getNotificaciones(): Flow<Resource<List<NotificacionEntity>>> = flow{
        try {
            emit(Resource.Loading())
            val notificaciones = notificacionRemoteDataSource.getNotificaciones()

            notificaciones.forEach {
                notificacionDao.addNotificacion(
                    it.toEntity()
                )
            }

            notificacionDao.getNotificaciones().collect{ notificacionesLocal ->
                emit(Resource.Success(notificacionesLocal))
            }

        } catch (e: HttpException){
            notificacionDao.getNotificaciones().collect{ notificacionesLocal ->
                emit(Resource.Success(notificacionesLocal))
            }

            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            notificacionDao.getNotificaciones().collect{ notificacionesLocal ->
                emit(Resource.Success(notificacionesLocal))
            }

            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }
}

private fun NotificacionDto.toEntity() = NotificacionEntity(
    notificacionId = notificacionId,
    usuarioId = usuarioId,
    descripcion = descripcion,
    fecha = fecha
)