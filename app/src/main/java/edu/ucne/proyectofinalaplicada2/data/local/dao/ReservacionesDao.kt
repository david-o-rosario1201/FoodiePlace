package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservacionesDao {
    @Upsert
    suspend fun save(reservacion: ReservacionesEntity)

    @Delete
    suspend fun delete(reservacion: ReservacionesEntity)

    @Query(
        """
            SELECT *
            FROM Reservaciones
            WHERE reservacionId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): ReservacionesEntity?

    @Query(
        """
            SELECT * FROM Reservaciones
            WHERE usuarioId = :usuarioId
            LIMIT 1
        """
    )
    suspend fun findUsuario(usuarioId: Int): ReservacionesEntity?

    @Query("SELECT * FROM Reservaciones")
    fun getAll(): Flow<List<ReservacionesEntity>>
}
