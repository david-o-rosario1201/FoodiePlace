package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.AlgoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlgoDao {
    @Upsert()
    suspend fun save(algo: AlgoEntity)

    @Query(
        """
            SELECT *
            FROM Algo
            WHERE AlgoId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): AlgoEntity?

    @Update
    suspend fun update(algo: AlgoEntity)

    @Delete
    suspend fun delete(algo: AlgoEntity)

    @Query("SELECT * FROM Algo")
    fun getAll(): Flow<List<AlgoEntity>>


}