package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.NotificacionApi
import edu.ucne.proyectofinalaplicada2.data.remote.dto.NotificacionDto
import javax.inject.Inject

class NotificacionRemoteDataSource @Inject constructor(
    private val notificacionApi: NotificacionApi
) {
    suspend fun addNotificacion(notificacionDto: NotificacionDto) = notificacionApi.addNotificacion(notificacionDto)

    suspend fun deleteNotificacion(notificacionId: Int) = notificacionApi.deleteNotificacion(notificacionId)

    suspend fun getNotificaciones() = notificacionApi.getNotificaciones()
}