package edu.ucne.proyectofinalaplicada2.data.remote

import edu.ucne.proyectofinalaplicada2.data.remote.API.ReseñasAPI
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReseñasDTO
import javax.inject.Inject

class ReseñasRemoteSource @Inject constructor(
    private val reseñasApi: ReseñasAPI
) {
    suspend fun getReseñas() = reseñasApi.getReseñas()

    suspend fun getReseñaById(id: Int) = reseñasApi.getReseñaById(id)

    suspend fun postReseña(reseña: ReseñasDTO?) = reseñasApi.postReseña(reseña)

    suspend fun putReseña(id: Int, reseña: ReseñasDTO) = reseñasApi.putReseña(id, reseña)

    suspend fun deleteReseña(id: Int) = reseñasApi.deleteReseña(id)

}