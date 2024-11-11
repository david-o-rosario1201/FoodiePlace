package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Upsert
    suspend fun save(review: ReviewEntity)

    @Delete
    suspend fun delete(review: ReviewEntity)

    @Query(
        """
            SELECT *
            FROM Review
            WHERE resenaId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): ReviewEntity?

    @Query(
        """
            SELECT * FROM Review
            WHERE usuarioId = :usuarioId
            LIMIT 1
        """
    )
    suspend fun findUsuario(usuarioId: Int): ReviewEntity?


    @Query("SELECT * FROM Review")
    fun getAll(): Flow<List<ReviewEntity>>


}