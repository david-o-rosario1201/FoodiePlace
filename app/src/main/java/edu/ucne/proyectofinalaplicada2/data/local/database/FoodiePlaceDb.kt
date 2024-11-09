package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [UsuarioEntity::class]
)

abstract class FoodiePlaceDb : RoomDatabase(){
    abstract fun usuarioDao(): UsuarioDao
}