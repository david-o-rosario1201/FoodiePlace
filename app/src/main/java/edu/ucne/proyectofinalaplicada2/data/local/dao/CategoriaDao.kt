package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {
    @Upsert()
    suspend fun save(categoria: CategoriaEntity)

    @Delete
    suspend fun deleteCategoria(categoria: CategoriaEntity)

    @Query(
        """
            SELECT * 
            FROM Categorias
            WHERE categoriaId=:id
            LIMIT 1
        """
    )
    suspend fun  getCategoriaById(id: Int): CategoriaEntity?


    @Query("SELECT * FROM Categorias")
    fun getAll(): Flow<List<CategoriaEntity>>

}