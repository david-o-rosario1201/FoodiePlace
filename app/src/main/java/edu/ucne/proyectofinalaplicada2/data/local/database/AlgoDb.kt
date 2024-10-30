package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.proyectofinalaplicada2.data.local.dao.AlgoDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.AlgoEntity

@Database(
    entities = [
        AlgoEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class AlgoDb : RoomDatabase(){
    abstract fun AlgoDao() : AlgoDao
}