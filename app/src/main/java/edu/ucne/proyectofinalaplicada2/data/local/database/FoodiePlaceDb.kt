package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.proyectofinalaplicada2.data.local.dao.ProductoDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReseñasDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReseñasEntity

@Database(
    entities = [
        ReseñasEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class FoodiePlaceDb : RoomDatabase(){
    abstract val ReseñasEntity: ReseñasDao
    abstract val ProductoDao: ProductoDao

}