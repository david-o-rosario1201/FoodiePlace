package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.PedidoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PedidoApi {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Pedidos")
    suspend fun addPedido(@Body pedido: PedidoDto): PedidoDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Pedidos/{pedidoId}")
    suspend fun getPedido(@Path("pedidoId") pedidoId: Int): PedidoDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Pedidos/{pedidoId}")
    suspend fun deletePedido(@Path("pedidoId") pedidoId: Int): Response<Unit>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @PUT("api/Pedidos/{pedidoId}")
    suspend fun updatePedido(
        @Path("pedidoId") pedidoId: Int,
        @Body pedido: PedidoDto
    ): Response<PedidoDto>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Pedidos")
    suspend fun getPedidos(): List<PedidoDto>
}