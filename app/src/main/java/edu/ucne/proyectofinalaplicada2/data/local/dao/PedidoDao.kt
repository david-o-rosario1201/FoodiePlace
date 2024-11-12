package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Upsert
    suspend fun addPedido(pedidoEntity: PedidoEntity)

    @Query("""
        SELECT * FROM Pedidos
        WHERE pedidoId = :pedidoId
        LIMIT 1
    """)
    suspend fun getPedido(pedidoId: Int): PedidoEntity?

    @Delete
    suspend fun deletePedido(pedidoEntity: PedidoEntity)

    @Query("SELECT * FROM Pedidos")
    fun getPedidos(): Flow<List<PedidoEntity>>
}