package edu.ucne.proyectofinalaplicada2.presentation.oferta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.OfertaDto
import edu.ucne.proyectofinalaplicada2.data.repository.OfertaRepository
import edu.ucne.proyectofinalaplicada2.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class OfertaViewModel @Inject constructor(
    private val ofertaRepository: OfertaRepository,
    private val productoRepository: ProductoRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(OfertaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getOfertas()
        getProductos()
    }

    private fun getOfertas(){
        viewModelScope.launch {
            ofertaRepository.getOfertas().collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                ofertas = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                ofertas = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getProductos(){
        viewModelScope.launch {
            productoRepository.getProductos().collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                productos = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                productos = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = result.message
                            )
                        }
                    }
                }
            }
        }
    }

//    fun onEvent(event: OfertaUiEvent){
//        when(event){
//            is OfertaUiEvent.OfertaIdChanged -> {
//                _uiState.update {
//                    it.copy(ofertasId = event.ofertaId)
//                }
//            }
//            is OfertaUiEvent.ProductoIdChanged -> {
//                _uiState.update {
//                    it.copy(productoId = event.productoId)
//                }
//            }
//            is OfertaUiEvent.PrecioChanged -> {
//                _uiState.update {
//                    it.copy(precio = event.precio)
//                }
//            }
//            is OfertaUiEvent.DescuentoChanged -> {
//                _uiState.update {
//                    it.copy(descuento = event.descuento)
//                }
//            }
//            is OfertaUiEvent.PrecioOfertaChanged -> {
//                _uiState.update {
//                    it.copy(precioOferta = event.precioOferta)
//                }
//            }
//            is OfertaUiEvent.FechaInicioChanged -> {
//                _uiState.update {
//                    it.copy(fechaInicio = event.fechaInicio)
//                }
//            }
//            is OfertaUiEvent.FechaFinalChanged -> {
//                _uiState.update {
//                    it.copy(fechaFinal = event.fechaFinal)
//                }
//            }
//            is OfertaUiEvent.ImagenChanged -> {
//                _uiState.update {
//                    it.copy(imagen = event.imagen)
//                }
//            }
//            is OfertaUiEvent.SelectedOferta -> {
//                viewModelScope.launch {
//                    if(event.ofertaId > 0){
//                        val oferta = ofertaRepository.getOferta(event.ofertaId)
//                        _uiState.update {
//                            it.copy(
//                                ofertasId = oferta.ofertasId,
//                                productoId = oferta.productoId,
//                                precio = oferta.precio,
//                                descuento = oferta.descuento,
//                                precioOferta = oferta.precioOferta,
//                                fechaInicio = oferta.fechaInicio,
//                                fechaFinal = oferta.fechaFinal,
//                                imagen = oferta.imagen
//                            )
//                        }
//                    }
//                }
//            }
//            is OfertaUiEvent.IsRefreshingChanged -> {
//                _uiState.update {
//                    it.copy(isRefreshing = event.isRefreshing)
//                }
//            }
//            OfertaUiEvent.Save -> {
//                viewModelScope.launch {
//                    if(_uiState.value.productoId == 0){
//                        _uiState.update {
//                            it.copy(errorProductoId = "El campo productoId no puede estar vacío")
//                        }
//                    }
//
//                    if(_uiState.value.precio == BigDecimal.valueOf(0.0)){
//                        _uiState.update {
//                            it.copy(errorPrecio = "El campo precio no puede estar vacío")
//                        }
//                    }
//
//                    if(_uiState.value.descuento == BigDecimal.valueOf(0.0)){
//                        _uiState.update {
//                            it.copy(errorDescuento = "El campo descuento no puede estar vacío")
//                        }
//                    }
//
//                    if(_uiState.value.precioOferta == BigDecimal.valueOf(0.0)){
//                        _uiState.update {
//                            it.copy(errorPrecioOferta = "El campo precio oferta no puede estar vacío")
//                        }
//                    }
//
//                    if(_uiState.value.fechaInicio == null){
//                        _uiState.update {
//                            it.copy(errorFechaInicio = "El campo fecha inicio no puede estar vacío")
//                        }
//                    }
//
//                    if(_uiState.value.fechaFinal == null){
//                        _uiState.update {
//                            it.copy(errorFechaFinal = "El campo fecha final no puede estar vacío")
//                        }
//                    }
//
//                    if(_uiState.value.imagen.toInt() == 0){
//                        _uiState.update {
//                            it.copy(errorImagen = "El campo imágen no puede estar vacío")
//                        }
//                    }
//
//                    if(_uiState.value.errorProductoId == "" && _uiState.value.errorPrecio == ""
//                        && _uiState.value.errorDescuento == "" && _uiState.value.errorPrecioOferta == ""
//                        && _uiState.value.errorFechaInicio == "" && _uiState.value.errorFechaFinal == ""
//                        && _uiState.value.errorImagen == ""){
//
//                        if(_uiState.value.ofertasId == null)
//                            ofertaRepository.addOferta(_uiState.value.toDto())
//                        else
//                        {
//                            ofertaRepository.updateOferta(
//                                _uiState.value.ofertasId ?: 0,
//                                _uiState.value.toDto()
//                            )
//                        }
//
//                        _uiState.update {
//                            it.copy(isSuccess = true)
//                        }
//                    }
//                }
//            }
//            OfertaUiEvent.Delete -> {
//                viewModelScope.launch {
//                    ofertaRepository.deleteOferta(_uiState.value.ofertasId ?: 0)
//                }
//            }
//            OfertaUiEvent.Refresh -> {
//                getOfertas()
//                getProductos()
//            }
//        }
//    }

    fun onEvent(event: OfertaUiEvent) {
        when (event) {
            is OfertaUiEvent.OfertaIdChanged -> {
                _uiState.update { it.copy(ofertasId = event.ofertaId) }
            }
            is OfertaUiEvent.ProductoIdChanged -> {
                _uiState.update { it.copy(productoId = event.productoId) }
            }
            is OfertaUiEvent.PrecioChanged -> {
                _uiState.update { it.copy(precio = event.precio) }
            }
            is OfertaUiEvent.DescuentoChanged -> {
                _uiState.update { it.copy(descuento = event.descuento) }
            }
            is OfertaUiEvent.PrecioOfertaChanged -> {
                _uiState.update { it.copy(precioOferta = event.precioOferta) }
            }
            is OfertaUiEvent.FechaInicioChanged -> {
                _uiState.update { it.copy(fechaInicio = event.fechaInicio) }
            }
            is OfertaUiEvent.FechaFinalChanged -> {
                _uiState.update { it.copy(fechaFinal = event.fechaFinal) }
            }
            is OfertaUiEvent.ImagenChanged -> {
                _uiState.update { it.copy(imagen = event.imagen) }
            }
            is OfertaUiEvent.SelectedOferta -> {
                cargarOfertaSeleccionada(event.ofertaId)
            }
            OfertaUiEvent.Save -> {
                viewModelScope.launch {
                    if (validarCampos()) {
                        guardarOferta()
                        _uiState.update { it.copy(isSuccess = true) }
                    }
                }
            }
            OfertaUiEvent.Delete -> {
                eliminarOferta()
            }
            OfertaUiEvent.Refresh -> {
                getOfertas()
                getProductos()
            }

            else -> {}
        }
    }

    private fun validarCampos(): Boolean {
        var isValid = true
        _uiState.update {
            it.copy(
                errorProductoId = if (it.productoId == 0) {
                    isValid = false
                    "El campo productoId no puede estar vacío"
                } else null,
                errorPrecio = if (it.precio == BigDecimal.ZERO) {
                    isValid = false
                    "El campo precio no puede estar vacío"
                } else null,
                errorDescuento = if (it.descuento == BigDecimal.ZERO) {
                    isValid = false
                    "El campo descuento no puede estar vacío"
                } else null,
                errorPrecioOferta = if (it.precioOferta == BigDecimal.ZERO) {
                    isValid = false
                    "El campo precio oferta no puede estar vacío"
                } else null,
                errorFechaInicio = if (it.fechaInicio == null) {
                    isValid = false
                    "El campo fecha inicio no puede estar vacío"
                } else null,
                errorFechaFinal = if (it.fechaFinal == null) {
                    isValid = false
                    "El campo fecha final no puede estar vacío"
                } else null,
                errorImagen = if (it.imagen.toInt() == 0) {
                    isValid = false
                    "El campo imágen no puede estar vacío"
                } else null
            )
        }
        return isValid
    }

    private fun cargarOfertaSeleccionada(ofertaId: Int) = viewModelScope.launch {
        if (ofertaId > 0) {
            val oferta = ofertaRepository.getOferta(ofertaId)
            _uiState.update {
                it.copy(
                    ofertasId = oferta.ofertasId,
                    productoId = oferta.productoId,
                    precio = oferta.precio,
                    descuento = oferta.descuento,
                    precioOferta = oferta.precioOferta,
                    fechaInicio = oferta.fechaInicio,
                    fechaFinal = oferta.fechaFinal,
                    imagen = oferta.imagen
                )
            }
        }
    }

    private suspend fun guardarOferta() {
        if (_uiState.value.ofertasId == null) {
            ofertaRepository.addOferta(_uiState.value.toDto())
        } else {
            ofertaRepository.updateOferta(_uiState.value.ofertasId ?: 0, _uiState.value.toDto())
        }
    }

    private fun eliminarOferta() = viewModelScope.launch {
        ofertaRepository.deleteOferta(_uiState.value.ofertasId ?: 0)
    }


    private fun OfertaUiState.toDto() = OfertaDto(
        ofertasId = ofertasId,
        productoId = productoId ?: 0,
        precioOferta = precioOferta ?: BigDecimal.valueOf(0.0),
        descuento = descuento ?: BigDecimal.valueOf(0.0),
        fechaInicio = fechaInicio ?: Date(),
        fechaFinal = fechaFinal ?: Date(),
        imagen = imagen,
        precio = precio ?: BigDecimal.valueOf(0.0)
    )
}