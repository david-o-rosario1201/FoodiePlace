package edu.ucne.proyectofinalaplicada2.presentation.algo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlgoViewModel @Inject constructor(
    //private val algoRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(AlgoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAlgos()
    }

    private fun getAlgos(){

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

                    }
                }
            }
            AlgoUiEvent.Save -> TODO()
            AlgoUiEvent.Delete -> TODO()
        }
    }

    //fun AlgoUiState.toEntity() =
}