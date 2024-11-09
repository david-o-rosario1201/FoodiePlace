package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.FoodiePlaceApi
import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodiePlaceApi: FoodiePlaceApi
) {
    //Usuarios
    suspend fun addUsuario(usuarioDto: UsuarioDto) = foodiePlaceApi.addUsuario(usuarioDto)

    suspend fun getUsuario(usuarioId: Int) = foodiePlaceApi.getUsuario(usuarioId)

    suspend fun deleteUsuario(usuarioId: Int) = foodiePlaceApi.deleteUsuario(usuarioId)

    suspend fun updateUsuario(usuarioId: Int, usuario: UsuarioDto) = foodiePlaceApi.updateUsuario(usuarioId,usuario)

    suspend fun getUsuarios() = foodiePlaceApi.getUsuarios()
}