package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.local.dto.ReservacionesDto
import edu.ucne.proyectofinalaplicada2.data.remote.API.ReservacionesAPI
import javax.inject.Inject

class ReservacionesDataSource @Inject constructor(
    private val reservacionesAPI: ReservacionesAPI
){
        suspend fun getReservaciones() = reservacionesAPI.getReservaciones()
        suspend fun getReservacionById(id: Int) = reservacionesAPI.getReservacionById(id)
        suspend fun postReservacion(reservacion: ReservacionesDto) = reservacionesAPI.postReservacion(reservacion)
        suspend fun  putReservacion(id: Int, reservacion: ReservacionesDto) = reservacionesAPI.putReservacion(id, reservacion)
        suspend fun deleteReservacion(id: Int) = reservacionesAPI.deleteReservacion(id)
}