package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.proyectofinalaplicada2.data.local.dao.CategoriaDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReservacionesDao


import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity

@Database(
    entities = [
        ReviewEntity::class,
        UsuarioEntity::class,
        CategoriaEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class FoodiePlaceDb : RoomDatabase(){

    abstract val ReservacionesEntity: ReservacionesDao

    abstract fun reviewDao(): ReviewDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun categoriaDao(): CategoriaDao

}