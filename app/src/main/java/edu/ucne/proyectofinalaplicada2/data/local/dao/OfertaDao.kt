package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OfertaDao {
    @Upsert
    suspend fun addOferta(ofertaEntity: OfertaEntity)

    @Query("""
        SELECT * FROM Ofertas
        WHERE ofertasId = :ofertaId
        LIMIT 1
    """)
    suspend fun getOferta(ofertaId: Int): OfertaEntity

    @Delete
    suspend fun deleteOferta(ofertaEntity: OfertaEntity)

    @Query("SELECT * FROM Ofertas")
    fun getOfertas(): Flow<List<OfertaEntity>>
}