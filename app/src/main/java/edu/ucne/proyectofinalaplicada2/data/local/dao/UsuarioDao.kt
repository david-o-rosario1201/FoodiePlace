package edu.ucne.proyectofinalaplicada2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Upsert()
    suspend fun addUsuario(usuario: UsuarioEntity)

    @Query("""
        SELECT * FROM Usuarios
        WHERE usuarioId = :usuarioId
        LIMIT 1
    """)
    suspend fun getUsuario(usuarioId: Int): UsuarioEntity?

    @Query("""
        SELECT * FROM Usuarios
        WHERE correo = :correo
        LIMIT 1
    """)
    suspend fun getUsuarioCorreo(correo: String): UsuarioEntity?


    @Query("""
        SELECT * FROM Usuarios
        WHERE correo = :correo
        LIMIT 1 
    """)
    suspend fun getUsuarioId(correo: String): UsuarioEntity?

    @Delete
    suspend fun deleteUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM Usuarios")
    fun getUsuarios(): Flow<List<UsuarioEntity>>
}