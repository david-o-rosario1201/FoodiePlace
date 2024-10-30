package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.AlgoDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.AlgoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AlgoRepository @Inject constructor(
    private val AlgoDao: AlgoDao)
{
    suspend fun save(algo: AlgoEntity) = AlgoDao.save(algo)
    suspend fun delete(algo: AlgoEntity) = AlgoDao.delete(algo)
    fun getAll() = AlgoDao.getAll()
    suspend fun find(id: Int) = AlgoDao.find(id)
}