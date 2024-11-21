package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.proyectofinalaplicada2.data.local.dao.PagosDao
import androidx.room.TypeConverters
import edu.ucne.proyectofinalaplicada2.data.local.dao.OfertaDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.CategoriaDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReservacionesDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ReviewDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.PagosEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReservacionesEntity
import edu.ucne.proyectofinalaplicada2.data.local.dao.CarritoDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.CarritoDetalleDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.ProductoDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.TarjetaDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.CategoriaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ReviewEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.CarritoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.NotificacionEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.ProductoEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.TarjetaEntity
import edu.ucne.proyectofinalaplicada2.data.local.dao.NotificacionDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.PedidoDao
import edu.ucne.proyectofinalaplicada2.data.local.dao.PedidoDetalleDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoEntity

@Database(
    entities = [
        PagosEntity::class,
        ReviewEntity::class,
        UsuarioEntity::class,
        ProductoEntity::class,
        ReservacionesEntity::class,
        CategoriaEntity::class,
        CarritoEntity::class,
        CarritoDetalleEntity::class,
        OfertaEntity::class,
        TarjetaEntity::class,
        PedidoEntity::class,
        PedidoDetalleEntity::class,
        NotificacionEntity::class
    ],
    version = 10,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class FoodiePlaceDb : RoomDatabase(){

    abstract fun ProductoDao(): ProductoDao
    abstract fun reservacionesDao(): ReservacionesDao
    abstract fun reviewDao(): ReviewDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pagosDao(): PagosDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun carritoDao(): CarritoDao
    abstract fun carritoDetalleDao(): CarritoDetalleDao
    abstract fun ofertaDao(): OfertaDao
    abstract fun tarjetaDao(): TarjetaDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun pedidoDetalleDao(): PedidoDetalleDao
    abstract fun notificacionDao(): NotificacionDao
}