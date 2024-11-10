package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.ReseñasAPI
import edu.ucne.proyectofinalaplicada2.data.remote.dto.ReseñasDTO
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ReseñasAPI: ReseñasAPI
) {
    suspend fun postReseña(reseña: ReseñasDTO) = ReseñasAPI.postReseña(reseña)
    suspend fun getReseñaById(id: Int) = ReseñasAPI.getReseñaById(id)
    suspend fun deleteReseña(id: Int) = ReseñasAPI.deleteReseña(id)
    suspend fun putReseña(id: Int, reseña: ReseñasDTO) = ReseñasAPI.putReseña(id, reseña)
    suspend fun getReseñas() = ReseñasAPI.getReseñas()

}