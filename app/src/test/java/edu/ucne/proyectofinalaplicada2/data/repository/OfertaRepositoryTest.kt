package edu.ucne.proyectofinalaplicada2.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.proyectofinalaplicada2.data.local.dao.OfertaDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.OfertaEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.OfertaRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.OfertaDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import java.math.BigDecimal
import java.util.Date

class OfertaRepositoryTest {

    @Test
    fun `Should add an oferta`() = runTest{
        // Given
        val oferta = OfertaDto(
            ofertasId = 2,
            productoId = 1,
            precio = BigDecimal.ZERO,
            descuento = BigDecimal.ZERO,
            precioOferta = BigDecimal.ZERO,
            fechaInicio = Date(),
            fechaFinal = Date(),
            imagen = 0
        )

        val ofertaRemoteDataSource = mockk<OfertaRemoteDataSource>()
        val ofertaDao = mockk<OfertaDao>()
        val repository = OfertaRepository(ofertaRemoteDataSource, ofertaDao)

        coEvery { ofertaRemoteDataSource.addOferta(any()) } returns oferta

        // When
        repository.addOferta(oferta)

        // Then
        coVerify { ofertaRemoteDataSource.addOferta(oferta) }
    }

    @Test
    fun `Should return an oferta`() = runTest{
        //Given
        val oferta = OfertaDto(
            ofertasId = 2,
            productoId = 1,
            precio = BigDecimal.ZERO,
            descuento = BigDecimal.ZERO,
            precioOferta = BigDecimal.ZERO,
            fechaInicio = Date(),
            fechaFinal = Date(),
            imagen = 0
        )

        val ofertaRemoteDataSource = mockk<OfertaRemoteDataSource>()
        val ofertaDao = mockk<OfertaDao>()
        val repository = OfertaRepository(ofertaRemoteDataSource, ofertaDao)

        coEvery { ofertaRemoteDataSource.getOferta(oferta.ofertasId ?: 0) } returns oferta

        //When
        repository.getOferta(oferta.ofertasId ?: 0)

        //Then
        coVerify { ofertaRemoteDataSource.getOferta(oferta.ofertasId ?: 0) }
    }

    @Test
    fun `Should delete an oferta`() = runTest{
        //Given
        val oferta = OfertaDto(
            ofertasId = 2,
            productoId = 1,
            precio = BigDecimal.ZERO,
            descuento = BigDecimal.ZERO,
            precioOferta = BigDecimal.ZERO,
            fechaInicio = Date(),
            fechaFinal = Date(),
            imagen = 0
        )

        val ofertaRemoteDataSource = mockk<OfertaRemoteDataSource>()
        val ofertaDao = mockk<OfertaDao>()
        val repository = OfertaRepository(ofertaRemoteDataSource, ofertaDao)

        coEvery { ofertaRemoteDataSource.deleteOferta(oferta.ofertasId ?: 0) } returns Response.success(Unit)

        //When
        repository.deleteOferta(oferta.ofertasId ?: 0)

        //Then
        coVerify { ofertaRemoteDataSource.deleteOferta(oferta.ofertasId ?: 0) }
    }

    @Test
    fun `Should update an oferta`() = runTest{
        //Given
        val oferta = OfertaDto(
            ofertasId = 2,
            productoId = 1,
            precio = BigDecimal.ZERO,
            descuento = BigDecimal.ZERO,
            precioOferta = BigDecimal.ZERO,
            fechaInicio = Date(),
            fechaFinal = Date(),
            imagen = 0
        )

        val ofertaRemoteDataSource = mockk<OfertaRemoteDataSource>()
        val ofertaDao = mockk<OfertaDao>()
        val repository = OfertaRepository(ofertaRemoteDataSource, ofertaDao)

        coEvery { ofertaRemoteDataSource.updateOferta(oferta.ofertasId ?: 0, oferta) } returns Response.success(oferta)

        //When
        repository.updateOferta(oferta.ofertasId ?: 0, oferta)

        //Then
        coVerify { ofertaRemoteDataSource.updateOferta(oferta.ofertasId ?: 0, oferta) }
    }

    @Test
    fun `Should return a flow of ofertas`() = runTest{
        //Given
        val ofertas = listOf(
            OfertaEntity(
                ofertasId = 2,
                productoId = 1,
                precio = BigDecimal.ZERO,
                descuento = BigDecimal.ZERO,
                precioOferta = BigDecimal.ZERO,
                fechaInicio = Date(),
                fechaFinal = Date(),
                imagen = 0
            )
        )

        val ofertaRemoteDataSource = mockk<OfertaRemoteDataSource>()
        val ofertaDao = mockk<OfertaDao>()
        val repository = OfertaRepository(ofertaRemoteDataSource, ofertaDao)

        coEvery { ofertaDao.getOfertas() } returns flowOf(ofertas)

        //When
        repository.getOfertas().test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(ofertas)

            cancelAndIgnoreRemainingEvents()
        }
    }
}