package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Upsert()
    suspend fun upsertProducto(producto: ProductoEntity)

    @Query("""
        SELECT * 
        FROM Productos
        WHERE productoId=:id
        LIMIT 1
        
    """
    )
    suspend fun getProductoById(id: Int): ProductoEntity?

    @Delete
    suspend fun deleteProducto(producto: ProductoEntity)

    @Query("""
        SELECT *
        FROM Productos
    """)
    suspend fun getProductos(): Flow<List<ProductoEntity>>
}