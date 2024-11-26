package edu.ucne.proyectofinalaplicada2.presentation.tarjeta

import edu.ucne.proyectofinalaplicada2.data.remote.dto.TarjetaDto

data class TarjetaUiState(
    val tarjetaId: Int? = null,
    val usuarioId: Int? = null,
    val tipoTarjeta: String? = "",
    val numeroTarjeta: String? = "",
    val fechaExpiracion: String? = "",
    val cvv: String? = "",
    val tarjetas: List<TarjetaDto> = emptyList(),
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMessge: String = "",
    val isRefreshing: Boolean = false
)
