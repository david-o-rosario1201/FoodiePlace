package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReservacionesDto
import edu.ucne.proyectofinalaplicada2.data.remote.API.ReservacionesAPI

import javax.inject.Inject

class ReservacionesRemoteDataSource @Inject constructor(
        private val reservacionesApi: ReservacionesAPI
) {
        suspend fun getReservaciones() = reservacionesApi.getReservaciones()

        suspend fun getReservacionById(id: Int) = reservacionesApi.getReservacionById(id)

        suspend fun addReservacion(reservacion: ReservacionesDto) = reservacionesApi.postReservacion(reservacion)

        suspend fun updateReservacion(id: Int, reservacion: ReservacionesDto) = reservacionesApi.putReservacion(id, reservacion)

        suspend fun deleteReservacion(id: Int) = reservacionesApi.deleteReservacion(id)
}
