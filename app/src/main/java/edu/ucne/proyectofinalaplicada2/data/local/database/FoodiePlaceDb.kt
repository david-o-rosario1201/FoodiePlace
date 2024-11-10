package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReseñasDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReseñasEntity
import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity

@Database(
    entities = [
        ReseñasEntity::class,
        UsuarioEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class FoodiePlaceDb : RoomDatabase(){
    abstract val ReseñasEntity: ReseñasDao

    abstract fun usuarioDao(): UsuarioDao
}