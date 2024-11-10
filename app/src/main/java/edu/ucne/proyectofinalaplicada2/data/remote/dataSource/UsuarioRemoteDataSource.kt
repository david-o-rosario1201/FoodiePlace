package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.UsuarioApi
import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val usuarioApi: UsuarioApi
) {
    suspend fun addUsuario(usuarioDto: UsuarioDto) = usuarioApi.addUsuario(usuarioDto)

    suspend fun getUsuario(usuarioId: Int) = usuarioApi.getUsuario(usuarioId)

    suspend fun deleteUsuario(usuarioId: Int) = usuarioApi.deleteUsuario(usuarioId)

    suspend fun updateUsuario(usuarioId: Int, usuario: UsuarioDto) = usuarioApi.updateUsuario(usuarioId,usuario)

    suspend fun getUsuarios() = usuarioApi.getUsuarios()
}