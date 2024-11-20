package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.NotificacionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificacionApi {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Notificaciones")
    suspend fun addNotificacion(@Body notificacionDto: NotificacionDto): NotificacionDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Notificaciones/{notificacionId}")
    suspend fun deleteNotificacion(@Path("notificacionId") notificacionId: Int): Response<Unit>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Notificaciones")
    suspend fun getNotificaciones(): List<NotificacionDto>
}