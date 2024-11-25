package edu.ucne.proyectofinalaplicada2.data.repository

import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.UsuarioRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val remoteDataSource: UsuarioRemoteDataSource,
    private val usuarioDao: UsuarioDao
){
    suspend fun addUsuario(usuarioDto: UsuarioDto){
        remoteDataSource.addUsuario(usuarioDto)
        addUsuarioLocal(usuarioDto)
    }

    suspend fun getUsuario(usuarioId: Int) = remoteDataSource.getUsuario(usuarioId)

    suspend fun deleteUsuario(usuarioId: Int) = remoteDataSource.deleteUsuario(usuarioId)

    suspend fun updateUsuario(usuarioId: Int, usuario: UsuarioDto){
        remoteDataSource.updateUsuario(usuarioId, usuario)
        addUsuarioLocal(usuario)
    }

    suspend fun getUsuarioCorreo(correo: String) = usuarioDao.getUsuarioCorreo(correo)

    suspend fun getUsuarioId(correo: String) = usuarioDao.getUsuarioId(correo)

    fun getUsuarios(): Flow<Resource<List<UsuarioEntity>>> = flow {
        try{
            emit(Resource.Loading())
            val usuarios = remoteDataSource.getUsuarios()

            usuarios.forEach {
                usuarioDao.addUsuario(
                    it.toEntity()
                )
            }

            usuarioDao.getUsuarios().collect{ usuariosLocal ->
                emit(Resource.Success(usuariosLocal))
            }

        } catch (e: HttpException){
            usuarioDao.getUsuarios().collect{ usuariosLocal ->
                emit(Resource.Success(usuariosLocal))
            }

            emit(Resource.Error("Error de internet ${e.message}"))
        } catch (e: Exception){
            usuarioDao.getUsuarios().collect{ usuariosLocal ->
                emit(Resource.Success(usuariosLocal))
            }

            emit(Resource.Error("Error desconocido ${e.message}"))
        }
    }

    private suspend fun addUsuarioLocal(usuarioDto: UsuarioDto) = usuarioDao.addUsuario(usuarioDto.toEntity())
}

private fun UsuarioDto.toEntity() = UsuarioEntity(
    usuarioId = usuarioId,
    nombre = nombre,
    telefono = telefono,
    correo = correo,
    contrasena = contrasena,
    fotoPerfil = fotoPerfil
)