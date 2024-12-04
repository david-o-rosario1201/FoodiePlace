package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.TarjetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TarjetaDao {
    @Upsert
    suspend fun addTarjeta(tarjeta: TarjetaEntity)

    @Query("""
        SELECT * 
        FROM Tarjetas
        WHERE tarjetaId=:id
        LIMIT 1
    """)
    suspend fun getTarjetaById(id: Int): TarjetaEntity?

    @Query("SELECT * FROM Tarjetas WHERE usuarioId = :usuarioId")
    fun getTarjetasPorUsuario(usuarioId: Int): Flow<List<TarjetaEntity>>

    @Query("SELECT * FROM Tarjetas WHERE usuarioId = :usuarioId")
    fun getTarjetasPorUsuario1(usuarioId: Int): List<TarjetaEntity>


    @Delete
    suspend fun deleteTarjeta(tarjeta: TarjetaEntity)

    @Query("""
        SELECT *
        FROM Tarjetas
    """)
    fun getTarjetas(): Flow<List<TarjetaEntity>>
}
