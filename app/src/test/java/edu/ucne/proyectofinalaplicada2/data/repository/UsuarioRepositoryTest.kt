package edu.ucne.proyectofinalaplicada2.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.proyectofinalaplicada2.data.local.dao.UsuarioDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.UsuarioEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.UsuarioRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import retrofit2.Response

class UsuarioRepositoryTest {

    @Test
    fun `Should add an usuario`() = runTest{
        // Given
        val usuario = UsuarioDto(
            usuarioId = 2,
            nombre = "usuario 2",
            telefono = "829-284-3031",
            correo = "usuario2@gmail.com",
            contrasena = "contrasena2"
        )

        val usuarioRemoteDataSource = mockk<UsuarioRemoteDataSource>()
        val usuarioDao = mockk<UsuarioDao>()
        val repository = UsuarioRepository(usuarioRemoteDataSource, usuarioDao)

        coEvery { usuarioRemoteDataSource.addUsuario(any()) } returns usuario

        // When
        repository.addUsuario(usuario)

        // Then
        coVerify { usuarioRemoteDataSource.addUsuario(usuario) }
    }

    @Test
    fun `Should return an usuario`() = runTest {
        //Given
        val usuario = UsuarioDto(
            usuarioId = 2,
            nombre = "usuario 2",
            telefono = "829-284-3031",
            correo = "usuario2@gmail.com",
            contrasena = "contrasena2"
        )

        val usuarioRemoteDataSource = mockk<UsuarioRemoteDataSource>()
        val usuarioDao = mockk<UsuarioDao>()
        val repository = UsuarioRepository(usuarioRemoteDataSource, usuarioDao)

        coEvery { usuarioRemoteDataSource.getUsuario(usuario.usuarioId ?: 0) } returns usuario

        //When
        repository.getUsuario(usuario.usuarioId ?: 0)

        //Then
        coVerify { usuarioRemoteDataSource.getUsuario(usuario.usuarioId ?: 0) }
    }

    @Test
    fun `Should delete an usuario`() = runTest {
        //Given
        val usuario = UsuarioDto(
            usuarioId = 2,
            nombre = "usuario 2",
            telefono = "829-284-3031",
            correo = "usuario2@gmail.com",
            contrasena = "contrasena2"
        )

        val usuarioRemoteDataSource = mockk<UsuarioRemoteDataSource>()
        val usuarioDao = mockk<UsuarioDao>()
        val repository = UsuarioRepository(usuarioRemoteDataSource, usuarioDao)

        coEvery { usuarioRemoteDataSource.deleteUsuario(usuario.usuarioId ?: 0) } returns Response.success(Unit)

        //When
        repository.deleteUsuario(usuario.usuarioId ?: 0)

        //Then
        coVerify { usuarioRemoteDataSource.deleteUsuario(usuario.usuarioId ?: 0) }
    }

    @Test
    fun `Should update an usuario`() = runTest {
        //Given
        val usuario = UsuarioDto(
            usuarioId = 2,
            nombre = "usuario 2",
            telefono = "829-284-3031",
            correo = "usuario2@gmail.com",
            contrasena = "contrasena2"
        )

        val usuarioRemoteDataSource = mockk<UsuarioRemoteDataSource>()
        val usuarioDao = mockk<UsuarioDao>()
        val repository = UsuarioRepository(usuarioRemoteDataSource, usuarioDao)

        coEvery { usuarioRemoteDataSource.updateUsuario(usuario.usuarioId ?: 0, usuario) } returns Response.success(usuario)

        //When
        repository.updateUsuario(usuario.usuarioId ?: 0, usuario)

        //Then
        coVerify { usuarioRemoteDataSource.updateUsuario(usuario.usuarioId ?: 0, usuario) }
    }

    @Test
    fun `Should return a flow of usuarios`() = runTest {
        //Given
        val usuarios = listOf(
            UsuarioEntity(
                usuarioId = 2,
                nombre = "usuario 2",
                telefono = "829-284-3031",
                correo = "usuario2@gmail.com",
                contrasena = "contrasena2"
            )
        )

        val usuarioRemoteDataSource = mockk<UsuarioRemoteDataSource>()
        val usuarioDao = mockk<UsuarioDao>()

        val repository = UsuarioRepository(usuarioRemoteDataSource,usuarioDao)

        coEvery { usuarioDao.getUsuarios() } returns flowOf(usuarios)

        //When
        repository.getUsuarios().test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(usuarios)

            cancelAndIgnoreRemainingEvents()
        }
    }
}