package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface CarritoDetalleDao {

    @Upsert
    suspend fun save(carritoDetalle: CarritoDetalleEntity)

    @Delete
    suspend fun deleteCarritoDetalle(carritoDetalle: CarritoDetalleEntity)

    @Query(
        """
            SELECT * FROM CarritoDetalle
            WHERE carritoDetalleId=:carritoId
            LIMIT 1
        """
    )
    fun getCarritoDetalles(carritoId: Int): Flow<List<CarritoDetalleEntity>>


    @Query(
        """
            SELECT * FROM CarritoDetalle
            WHERE carritoId=:id
        """
    )
    suspend fun carritoItem(id: Int): List<CarritoDetalleEntity>

    @Query("DELETE FROM CarritoDetalle WHERE carritoId = :carritoId")
    suspend fun clearCarrito(carritoId: Int)

    @Query(
        """
            SELECT * FROM CarritoDetalle
            WHERE productoId=:id and carritoId=:idCarrito
            LIMIT 1
        """
    )
    suspend fun getCarritoDetalleByProductoId(id: Int, idCarrito: Int): CarritoDetalleEntity?

    @Query(
        """
            SELECT EXISTS 
                (SELECT 1 
                 FROM carritoDetalle 
                 WHERE productoId = :productoId AND carritoId = :carritoId)
        """
    )
    suspend fun carritoDetalleExit(productoId: Int, carritoId: Int): Boolean

    @Query(
        """
        SELECT SUM((cantidad * precioUnitario) + impuesto + propina) AS total
        FROM CarritoDetalle
        WHERE carritoId = :carritoId
    """
    )
    suspend fun getTotalCarrito(carritoId: Int): BigDecimal?

    @Query(
        """
        SELECT SUM(cantidad * precioUnitario) AS subTotal
        FROM CarritoDetalle
        WHERE carritoId = :carritoId
    """
    )
    suspend fun getSubTotalCarrito(carritoId: Int): BigDecimal?


}