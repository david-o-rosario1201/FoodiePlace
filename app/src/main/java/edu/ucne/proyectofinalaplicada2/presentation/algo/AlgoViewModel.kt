package edu.ucne.proyectofinalaplicada2.presentation.algo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.local.entities.AlgoEntity
import edu.ucne.proyectofinalaplicada2.data.repository.AlgoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlgoViewModel @Inject constructor(
    private val algoRepository: AlgoRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(AlgoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAlgos()
    }

    private fun getAlgos(){
        viewModelScope.launch {
            algoRepository.getAll().collectLatest { algos ->
                _uiState.update {
                    it.copy(algos = algos)
                }
            }
        }
    }

    fun onEvent(event: AlgoUiEvent){
        when(event){
            is AlgoUiEvent.AlgoIdChanged -> {
                _uiState.update {
                    it.copy(algoId = event.algoId)
                }
            }
            is AlgoUiEvent.NombreChanged -> {
                _uiState.update {
                    it.copy(nombre = event.nombre)
                }
            }
            is AlgoUiEvent.TipoChanged -> {
                _uiState.update {
                    it.copy(tipo = event.tipo)
                }
            }
            is AlgoUiEvent.SelectedAlgo -> {
                viewModelScope.launch {
                    if(event.algoId > 0){
                        val algo = algoRepository.find(event.algoId)
                        _uiState.update {
                            it.copy(
                                algoId = algo?.AlgoId,
                                nombre = algo?.Nombre,
                                tipo = algo?.Tipo
                            )
                        }
                    }
                }
            }
            AlgoUiEvent.Save -> {
                viewModelScope.launch {
                    val nombreBuscado = _uiState.value.algos
                        .find { it.Nombre.lowercase() == _uiState.value.nombre?.lowercase() }

                    val tipoBuscado = _uiState.value.algos
                        .find { it.Tipo.lowercase() == _uiState.value.tipo?.lowercase() }

                    if(_uiState.value.nombre.isNullOrEmpty()) {
                        _uiState.update {
                            it.copy(errorNombre = "El nombre no puede estar vacío")
                        }
                    }
                    else if(nombreBuscado != null && nombreBuscado.AlgoId != _uiState.value.algoId){
                        _uiState.update {
                            it.copy(errorNombre = "Ya existe este nombre")
                        }
                    }

                    if(_uiState.value.tipo.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorTipo = "El tipo no puede estar vacío")
                        }
                    }
                    else if(tipoBuscado != null && tipoBuscado.AlgoId != _uiState.value.algoId){
                        _uiState.update {
                            it.copy(errorTipo = "Ya existe este tipo")
                        }
                    }

                    if(_uiState.value.errorNombre == "" && _uiState.value.errorTipo == ""){
                        algoRepository.save(_uiState.value.toEntity())
                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                    }
                }
            }
            AlgoUiEvent.Delete -> {
                viewModelScope.launch {
                    algoRepository.delete(_uiState.value.toEntity())
                }
            }
        }
    }

    private fun AlgoUiState.toEntity() = AlgoEntity(
        AlgoId = algoId,
        Nombre = nombre ?: "",
        Tipo = tipo ?: ""
    )
}