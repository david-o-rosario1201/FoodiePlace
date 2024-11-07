package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReseñasEntity
import kotlinx.coroutines.flow.Flow

interface ReseñasDao {
    @Upsert
    suspend fun save(reseñas: ReseñasEntity)

    @Delete
    suspend fun delete(reseñas: ReseñasEntity)

    @Query(
        """
            SELECT *
            FROM Reseñas
            WHERE resenaId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): ReseñasEntity?

    @Query(
        """
            SELECT * FROM RESEÑAS
            WHERE usuarioId = :usuarioId
            LIMIT 1
        """
    )
    suspend fun findUsuario(usuarioId: Int): ReseñasEntity?


    @Query("SELECT * FROM Reseñas")
    fun getAll(): Flow<List<ReseñasEntity>>


}