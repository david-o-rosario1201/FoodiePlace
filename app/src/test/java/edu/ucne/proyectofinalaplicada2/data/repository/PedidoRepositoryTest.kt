package edu.ucne.proyectofinalaplicada2.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.proyectofinalaplicada2.data.local.dao.PedidoDao
import edu.ucne.proyectofinalaplicada2.data.local.entities.PedidoEntity
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dataSource.PedidoRemoteDataSource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PedidoDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import java.math.BigDecimal
import java.util.Date

class PedidoRepositoryTest {

    @Test
    fun `Should add a pedido`() = runTest{
        // Given
        val pedido = PedidoDto(
            pedidoId = 1,
            usuarioId = 1,
            fechaPedido = Date(),
            total = BigDecimal.ZERO,
            paraLlevar = true,
            estado = "Pendiente",
            pedidoDetalle = emptyList()
        )

        val pedidoRemoteDataSource = mockk<PedidoRemoteDataSource>()
        val pedidoDao = mockk<PedidoDao>()
        val repository = PedidoRepository(pedidoRemoteDataSource,pedidoDao)

        coEvery { pedidoRemoteDataSource.addPedido(any()) } returns pedido

        // When
        repository.addPedido(pedido)

        // Then
        coVerify { pedidoRemoteDataSource.addPedido(pedido) }
    }

    @Test
    fun `Should return a pedido`() = runTest{
        //Given
        val pedido = PedidoDto(
            pedidoId = 1,
            usuarioId = 1,
            fechaPedido = Date(),
            total = BigDecimal.ZERO,
            paraLlevar = true,
            estado = "Pendiente",
            pedidoDetalle = emptyList()
        )

        val pedidoRemoteDataSource = mockk<PedidoRemoteDataSource>()
        val pedidoDao = mockk<PedidoDao>()
        val repository = PedidoRepository(pedidoRemoteDataSource,pedidoDao)

        coEvery { pedidoRemoteDataSource.getPedido(pedido.pedidoId ?: 0) } returns pedido

        //When
        repository.getPedido(pedido.pedidoId ?: 0)

        //Then
        coVerify { pedidoRemoteDataSource.getPedido(pedido.pedidoId ?: 0) }
    }

    @Test
    fun `Should delete a pedido`() = runTest{
        //Given
        val pedido = PedidoDto(
            pedidoId = 1,
            usuarioId = 1,
            fechaPedido = Date(),
            total = BigDecimal.ZERO,
            paraLlevar = true,
            estado = "Pendiente",
            pedidoDetalle = emptyList()
        )

        val pedidoRemoteDataSource = mockk<PedidoRemoteDataSource>()
        val pedidoDao = mockk<PedidoDao>()
        val repository = PedidoRepository(pedidoRemoteDataSource,pedidoDao)

        coEvery { pedidoRemoteDataSource.deletePedido(pedido.pedidoId ?: 0) } returns Response.success(Unit)

        //When
        repository.deletePedido(pedido.pedidoId ?: 0)

        //Then
        coVerify { pedidoRemoteDataSource.deletePedido(pedido.pedidoId ?: 0) }
    }

    @Test
    fun `Should update a pedido`() = runTest{
        //Given
        val pedido = PedidoDto(
            pedidoId = 1,
            usuarioId = 1,
            fechaPedido = Date(),
            total = BigDecimal.ZERO,
            paraLlevar = true,
            estado = "Pendiente",
            pedidoDetalle = emptyList()
        )

        val pedidoRemoteDataSource = mockk<PedidoRemoteDataSource>()
        val pedidoDao = mockk<PedidoDao>()
        val repository = PedidoRepository(pedidoRemoteDataSource,pedidoDao)

        coEvery { pedidoRemoteDataSource.updatePedido(pedido.pedidoId ?: 0, pedido) } returns Response.success(pedido)

        //When
        repository.updatePedido(pedido.pedidoId ?: 0, pedido)

        //Then
        coVerify { pedidoRemoteDataSource.updatePedido(pedido.pedidoId ?: 0, pedido) }
    }

    @Test
    fun `Should return a flow of pedidos`() = runTest{
        //Given
        val pedidos = listOf(
            PedidoEntity(
                pedidoId = 1,
                usuarioId = 1,
                fechaPedido = Date(),
                total = BigDecimal.ZERO,
                paraLlevar = true,
                estado = "Pendiente",
                pedidoDetalle = emptyList()
            )
        )

        val pedidoRemoteDataSource = mockk<PedidoRemoteDataSource>()
        val pedidoDao = mockk<PedidoDao>()
        val repository = PedidoRepository(pedidoRemoteDataSource,pedidoDao)

        coEvery { pedidoDao.getPedidos() } returns flowOf(pedidos)

        //When
        repository.getPedidos().test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(pedidos)

            cancelAndIgnoreRemainingEvents()
        }
    }
}