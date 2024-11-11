package edu.ucne.proyectofinalaplicada2.data.local.database

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoDetalleEntity
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDetalleDto
import java.math.BigDecimal
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @TypeConverter
    fun bigDecimalToString(bigDecimal: BigDecimal?): String? {
        return bigDecimal?.toPlainString()
    }

    private val gson = Gson()

    @TypeConverter
    fun fromCarritoDetalleList(value: List<CarritoDetalleDto>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCarritoDetalleList(value: String): List<CarritoDetalleDto> {
        val listType = object : TypeToken<List<CarritoDetalleDto>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromPedidosDetalleList(value: List<PedidoDetalleEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPedidosDetalleList(value: String): List<PedidoDetalleEntity> {
        val listType = object : TypeToken<List<PedidoDetalleEntity>>() {}.type
        return gson.fromJson(value, listType)
    }
}