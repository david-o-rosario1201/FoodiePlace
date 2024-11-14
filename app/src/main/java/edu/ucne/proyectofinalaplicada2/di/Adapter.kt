package edu.ucne.proyectofinalaplicada2.di

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Adapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    @ToJson
    fun toJson(value: Date?): String? {
        return value?.let { dateFormat.format(it) }
    }

    @FromJson
    fun fromJson(value: String?): Date? {
        return value?.let { dateFormat.parse(it) }
    }

    @ToJson
    fun toJson(value: BigDecimal): String {
        return value.toString()
    }

    @FromJson
    fun fromJson(value: String): BigDecimal {
        return BigDecimal(value)
    }
}