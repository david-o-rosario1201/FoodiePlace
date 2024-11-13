package edu.ucne.proyectofinalaplicada2.presentation.pedido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.PedidoDto
import edu.ucne.proyectofinalaplicada2.data.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PedidoViewModel @Inject constructor(
    private val pedidoRepository: PedidoRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(PedidoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPedidos()
    }

    private fun getPedidos(){
        viewModelScope.launch {
            pedidoRepository.getPedidos().collectLatest { result->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                pedidos = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                pedidos = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: PedidoUiEvent){
        when(event){
            is PedidoUiEvent.PedidoIdChanged -> {
                _uiState.update { it.copy(pedidoId = event.pedidoId)}
            }
            is PedidoUiEvent.UsuarioIdChanged -> {
                _uiState.update { it.copy(usuarioId = event.usuarioId)}
            }
            is PedidoUiEvent.FechaPedidoChanged -> {
                _uiState.update { it.copy(fechaPedido = event.fechaPedido)}
            }
            is PedidoUiEvent.TotalChanged -> {
                _uiState.update { it.copy(total = event.total)}
            }
            is PedidoUiEvent.ParaLlevarChanged -> {
                _uiState.update { it.copy(paraLlevar = event.paraLlevar)}
            }
            is PedidoUiEvent.EstadoChanged -> {
                _uiState.update { it.copy(estado = event.estado)}
            }
            is PedidoUiEvent.PedidoDetalleChanged -> {
                _uiState.update { it.copy(pedidoDetalle = event.pedidoDetalle)}
            }
            is PedidoUiEvent.IsRefreshingChanged -> {
                _uiState.update { it.copy(isRefreshing = event.isRefreshing)}
            }
            is PedidoUiEvent.SelectedPedido -> {
                cargarPedidoSeleccionado(event.pedidoId)
            }
            PedidoUiEvent.Save -> {
                viewModelScope.launch {
                    if(validarCampos()){
                        guardarPedido()
                        _uiState.update { it.copy(isSuccess = true) }
                    }
                }
            }
            PedidoUiEvent.Delete -> {
                viewModelScope.launch {
                    pedidoRepository.deletePedido(_uiState.value.pedidoId ?: 0)
                }
            }
            PedidoUiEvent.Refresh -> {
                getPedidos()
            }
        }
    }

    private suspend fun guardarPedido(){
        if(_uiState.value.pedidoId == null){
            pedidoRepository.addPedido(_uiState.value.toDto())
        } else{
            pedidoRepository.updatePedido(_uiState.value.pedidoId ?: 0,_uiState.value.toDto())
        }
    }

    private fun validarCampos(): Boolean{
        var isValid = true
        _uiState.update {
            it.copy(
                errorUsuarioId = if(it.usuarioId == 0){
                    isValid = false
                    "El campo usuarioId no puede estar vacío"
                } else null,
                errorFechaPedido = if(it.fechaPedido == null){
                    isValid = false
                    "El campo fecha pedido no puede estar vacío"
                } else null,
                errorTotal = if(it.total == BigDecimal.ZERO){
                    isValid = false
                    "El campo total no puede estar vacío"
                    } else null,
                errorParaLlevar = if(it.paraLlevar == null){
                    isValid = false
                    "El campo para llevar no puede estar vacío"
                    } else null,
                errorEstado = if(it.estado.isNullOrEmpty()){
                    isValid = false
                    "El campo estado no puede estar vacío"
                    } else null,
                errorPedidoDetalle = if(it.pedidoDetalle.isEmpty()){
                    isValid = false
                    "El campo pedido detalle no puede estar vacío"
                } else null
            )
        }
        return isValid
    }

    private fun cargarPedidoSeleccionado(pedidoId: Int) = viewModelScope.launch{
        if(pedidoId > 0){
            val pedido = pedidoRepository.getPedido(pedidoId)
            _uiState.update {
                it.copy(
                    pedidoId = pedido.pedidoId,
                    usuarioId = pedido.usuarioId,
                    fechaPedido = pedido.fechaPedido,
                    total = pedido.total,
                    paraLlevar = pedido.paraLlevar,
                    estado = pedido.estado,
                    pedidoDetalle = pedido.pedidoDetalle
                )
            }
        }
    }

    private fun PedidoUiState.toDto() = PedidoDto(
        pedidoId = pedidoId,
        usuarioId = usuarioId ?: 0,
        fechaPedido = fechaPedido ?: Date(),
        total = total ?: BigDecimal.valueOf(0.0),
        paraLlevar = paraLlevar ?: false,
        estado = estado ?: "",
        pedidoDetalle = pedidoDetalle
    )
}