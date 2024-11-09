package edu.ucne.proyectofinalaplicada2.data.remote.API

import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FoodiePlaceApi {
    @Headers("X-Api-Key:kaisokuni_orewanara")
    @POST("api/Usuarios")
    suspend fun addUsuario(@Body usuarioDto: UsuarioDto): UsuarioDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Usuarios/{usuarioId}")
    suspend fun getUsuario(@Path("usuarioId") usuarioId: Int): UsuarioDto

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @DELETE("api/Usuarios/{usuarioId}")
    suspend fun deleteUsuario(@Path("usuarioId") usuarioId: Int): Response<Unit>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @PUT("api/Usuarios/{usuarioId}")
    suspend fun updateUsuario(
        @Path("usuarioId") usuarioId: Int,
        @Body usuario: UsuarioDto
    ): Response<UsuarioDto>

    @Headers("X-Api-Key:kaisokuni_orewanara")
    @GET("api/Usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>
}