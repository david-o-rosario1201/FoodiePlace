package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.proyectofinalaplicada2.data.local.dao.OfertaDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.CategoriaDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReservacionesDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.CarritoDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.CarritoDetalleDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity

@Database(
    entities = [
        ReviewEntity::class,
        UsuarioEntity::class,
        OfertaEntity::class,
        CategoriaEntity::class,
        CarritoEntity::class,
        CarritoDetalleEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class FoodiePlaceDb : RoomDatabase(){

    abstract val ReservacionesEntity: ReservacionesDao

    abstract fun reviewDao(): ReviewDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun carritoDao(): CarritoDao
    abstract fun carritoDetalleDao(): CarritoDetalleDao
    abstract fun ofertaDao(): OfertaDao
}