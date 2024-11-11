package edu.ucne.proyectofinalaplicada2.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.proyectofinalaplicada2.data.local.dao.TarjetaDao
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.TarjetaRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.TarjetaDto
import edu.ucne.proyectofinalaplicada2.data.local.entities.TarjetaEntity // Asegúrate de que la importación a TarjetaEntity esté presente
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import retrofit2.Response
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TarjetaRepositoryTest {

    @Test
    fun `Should add an tarjeta`() = runTest {
        //Given
        val tarjeta = TarjetaDto(
            tarjetaId = 2,
            usuarioId = 1,
            tipoTarjeta = "Crédito",
            numeroTarjeta = "1234-5678-9012-3456",
            fechaExpiracion = "12/25",
            cvv = "123"
        )

        val tarjetaRemoteDataSource = mockk<TarjetaRemoteDataSource>()
        val tarjetaDao = mockk<TarjetaDao>()
        val repository = TarjetaRepository(tarjetaRemoteDataSource, tarjetaDao)

        coEvery { tarjetaRemoteDataSource.addTarjeta(any()) } returns tarjeta

        //When
        repository.addTarjeta(tarjeta)

        //Then
        coVerify { tarjetaRemoteDataSource.addTarjeta(tarjeta) }

    }

    @Test
    fun `Should return an tarjeta`() = runTest {
        //Given
        val tarjeta = TarjetaDto(
            tarjetaId = 2,
            usuarioId = 1,
            tipoTarjeta = "Crédito",
            numeroTarjeta = "1234-5678-9012-3456",
            fechaExpiracion = "12/25",
            cvv = "123"
        )

        val tarjetaRemoteDataSource = mockk<TarjetaRemoteDataSource>()
        val tarjetaDao = mockk<TarjetaDao>()
        val repository = TarjetaRepository(tarjetaRemoteDataSource, tarjetaDao)

        coEvery { tarjetaRemoteDataSource.getTarjeta(tarjeta.tarjetaId ?: 0) } returns tarjeta

        //When
        repository.getTarjeta(tarjeta.tarjetaId ?: 0)

        //Then
        coVerify { tarjetaRemoteDataSource.getTarjeta(tarjeta.tarjetaId ?: 0) }

    }

    @Test
    fun `Should delete an tarjeta`() = runTest {
        //Given
        val tarjeta = TarjetaDto(
            tarjetaId = 2,
            usuarioId = 1,
            tipoTarjeta = "Crédito",
            numeroTarjeta = "1234-5678-9012-3456",
            fechaExpiracion = "12/25",
            cvv = "123"
        )

        val tarjetaRemoteDataSource = mockk<TarjetaRemoteDataSource>()
        val tarjetaDao = mockk<TarjetaDao>()
        val repository = TarjetaRepository(tarjetaRemoteDataSource, tarjetaDao)

        coEvery { tarjetaRemoteDataSource.deleteTarjeta(tarjeta.tarjetaId ?: 0) } returns Response.success(Unit)

        //When
        repository.deleteTarjeta(tarjeta.tarjetaId ?: 0)

        //Then
        coVerify { tarjetaRemoteDataSource.deleteTarjeta(tarjeta.tarjetaId ?: 0) }


    }

    @Test
    fun `Should update an tarjeta`() = runTest {
        //Given
        val tarjeta = TarjetaDto(
            tarjetaId = 2,
            usuarioId = 1,
            tipoTarjeta = "Crédito",
            numeroTarjeta = "1234-5678-9012-3456",
            fechaExpiracion = "12/25",
            cvv = "123"
        )

        val tarjetaRemoteDataSource = mockk<TarjetaRemoteDataSource>()
        val tarjetaDao = mockk<TarjetaDao>()
        val repository = TarjetaRepository(tarjetaRemoteDataSource, tarjetaDao)

        coEvery { tarjetaRemoteDataSource.updateTarjeta(tarjeta.tarjetaId ?: 0, tarjeta) } returns Response.success(tarjeta)

        //When
        repository.updateTarjeta(tarjeta.tarjetaId ?: 0, tarjeta)

        //Then
        coVerify { tarjetaRemoteDataSource.updateTarjeta(tarjeta.tarjetaId ?: 0, tarjeta) }


    }

    @Test
    fun `Should return a flow of tarjetas`() = runTest {
        //Given
        val tarjetas = listOf(
            TarjetaEntity(
                tarjetaId = 2,
                usuarioId = 1,
                tipoTarjeta = "Crédito",
                numeroTarjeta = "1234-5678-9012-3456",
                fechaExpiracion = "12/25",
                cvv = "123"
            )
        )

        val tarjetaRemoteDataSource = mockk<TarjetaRemoteDataSource>()
        val tarjetaDao = mockk<TarjetaDao>()
        val repository = TarjetaRepository(tarjetaRemoteDataSource, tarjetaDao)

        coEvery { tarjetaDao.getTarjetas() } returns flowOf(tarjetas)

        //When
        repository.getTarjetas().test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(tarjetas)

            cancelAndIgnoreRemainingEvents()
        }

    }



}
