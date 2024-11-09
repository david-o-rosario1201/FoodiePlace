package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.PagosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PagosDao {
    @Upsert
    suspend fun save(pagos: PagosEntity)

    @Delete
    suspend fun delete(pagos: PagosEntity)

    @Query(
        """
            SELECT *
            FROM Pagos
            WHERE pagoId = :id
            LIMIT 1"""
    )
    suspend fun find(id: Int): PagosEntity?

    @Query(
        """
            SELECT * FROM Pagos
            WHERE pedidoId = :pedidoId
            LIMIT 1
        """
    )
    suspend fun findPedido(pedidoId: Int): PagosEntity?

    @Query("SELECT * FROM Pagos")
    fun getAll(): Flow<List<PagosEntity>>
}