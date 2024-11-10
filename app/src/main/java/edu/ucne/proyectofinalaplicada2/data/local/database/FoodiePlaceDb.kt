package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.proyectofinalaplicada2.data.local.dao.OfertaDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity

@Database(
    entities = [
        ReviewEntity::class,
        UsuarioEntity::class,
        OfertaEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class FoodiePlaceDb : RoomDatabase(){
    abstract fun reviewDao(): ReviewDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun ofertaDao(): OfertaDao
}