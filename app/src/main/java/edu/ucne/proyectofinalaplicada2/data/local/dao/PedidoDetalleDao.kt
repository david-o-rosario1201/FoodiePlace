package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDetalleDao {
    @Upsert
    suspend fun addPedidoDetalle(pedidoDetalleEntity: PedidoDetalleEntity)

    @Query("""
        SELECT * FROM PedidosDetalle
        WHERE pedidoDetalleId = :pedidoDetalleId
        LIMIT 1
    """)
    suspend fun getPedidoDetalle(pedidoDetalleId: Int): PedidoDetalleEntity?

    @Delete
    suspend fun deletePedidoDetalle(pedidoDetalleEntity: PedidoDetalleEntity)

    @Query("SELECT * FROM PedidosDetalle")
    fun getPedidosDetalle(): Flow<List<PedidoDetalleEntity>>
}