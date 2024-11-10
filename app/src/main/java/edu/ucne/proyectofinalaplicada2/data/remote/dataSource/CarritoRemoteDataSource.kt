package edu.ucne.proyectofinalaplicada2.data.remote.dataSource

import edu.ucne.proyectofinalaplicada2.data.remote.API.CarritoApi
import edu.ucne.proyectofinalaplicada2.data.remote.dto.CarritoDto
import javax.inject.Inject

class CarritoRemoteDataSource @Inject constructor(
    private val carritoApi: CarritoApi
) {
    suspend fun getCarrito() = carritoApi.getCarrito()
    suspend fun getCarritoById(id: Int) = carritoApi.getCarritoById(id)
    suspend fun postCarrito(carrito: CarritoDto) = carritoApi.postCarrito(carrito)
    suspend fun deleteCarrito(id: Int) = carritoApi.deleteCarrito(id)

}