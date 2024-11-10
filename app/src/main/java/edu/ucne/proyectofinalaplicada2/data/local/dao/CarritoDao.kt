package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Upsert
    suspend fun save(carrito: CarritoEntity)

    @Delete
    suspend fun deleteCarrito(carrito: CarritoEntity)

    @Query(
        """
            SELECT * 
            FROM Carrito
            WHERE carritoId=:id
            LIMIT 1
        """
    )
    suspend fun  getCarritoById(id: Int): CarritoEntity?

    @Query("""
        SELECT * FROM Carrito
        WHERE pagado = 0
        ORDER BY carritoId DESC LIMIT 1
    """)
    suspend fun getLastCarrito(): CarritoEntity?

    @Query(
        """
            SELECT *
            FROM carrito 
            WHERE pagado = 0 AND usuarioId = :usuarioId 
            ORDER BY carritoId DESC 
            LIMIT 1
        """
    )
    suspend fun getLastCarritoByUsuario(usuarioId: Int): CarritoEntity?


    @Query(
        """
           SELECT *
            FROM carrito
            WHERE usuarioId in (SELECT usuarioId FROM usuarios WHERE correo like :email)
            ORDER BY carritoId DESC 
            LIMIT 1;
        """
    )
    fun getLastCarritoByEmail(email: String): Flow<CarritoEntity>

    @Query("SELECT * FROM Carrito")
    fun getAll(): Flow<List<CarritoEntity>>
}