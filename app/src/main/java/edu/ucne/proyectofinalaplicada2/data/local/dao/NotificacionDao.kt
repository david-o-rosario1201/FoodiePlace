package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import edu.ucne.proyectofinalaplicada2.data.local.entities.NotificacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificacionDao {
    @Insert
    suspend fun addNotificacion(notificacionEntity: NotificacionEntity)

    @Delete
    suspend fun deleteNotificacion(notificacionEntity: NotificacionEntity)

    @Query("SELECT * FROM Notificaciones")
    fun getNotificaciones(): Flow<List<NotificacionEntity>>
}