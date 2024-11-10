package edu.ucne.proyectofinalaplicada2.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState = _uiState.asStateFlow()

    private fun getUsuarios(){
        viewModelScope.launch {
            usuarioRepository.getUsuarios().collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                usuarios = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                usuarios = result.data ?: emptyList(),
                                isLoading = false,
                                errorCargar = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: UsuarioUiEvent){
        when(event){
            is UsuarioUiEvent.UsuarioIdChanged -> {
                _uiState.update {
                    it.copy(usuarioId = event.usuarioId)
                }
            }
            is UsuarioUiEvent.NombreChanged -> {
                _uiState.update {
                    it.copy(nombre = event.nombre)
                }
            }
            is UsuarioUiEvent.TelefonoChanged -> {
                _uiState.update {
                    it.copy(telefono = event.telefono)
                }
            }
            is UsuarioUiEvent.CorreoChanged -> {
                _uiState.update {
                    it.copy(correo = event.correo)
                }
            }
            is UsuarioUiEvent.Contrasena -> {
                _uiState.update {
                    it.copy(contrasena = event.contrasena)
                }
            }
            is UsuarioUiEvent.SelectedUsuario -> {
                viewModelScope.launch {
                    if(event.usuarioId > 0){
                        val usuario = usuarioRepository.getUsuario(event.usuarioId)
                        _uiState.update {
                            it.copy(
                                usuarioId = usuario.usuarioId,
                                nombre = usuario.nombre,
                                telefono = usuario.telefono,
                                correo = usuario.correo,
                                contrasena = usuario.contrasena
                            )
                        }
                    }
                }
            }
            is UsuarioUiEvent.IsRefreshingChanged -> {
                _uiState.update {
                    it.copy(isRefreshing = event.isRefreshing)
                }
            }
            UsuarioUiEvent.Save -> {
                viewModelScope.launch {
                    if(_uiState.value.nombre.isNullOrBlank()){
                        _uiState.update {
                            it.copy(errorNombre = "El nombre no puede estar vacío")
                        }
                    }

                    if(_uiState.value.telefono.isNullOrBlank()){
                        _uiState.update {
                            it.copy(errorTelefono = "El teléfono no puede estar vacío")
                        }
                    }

                    if(_uiState.value.correo.isNullOrBlank()){
                        _uiState.update {
                            it.copy(errorCorreo = "El correo no puede estar vacío")
                        }
                    }

                    if(_uiState.value.contrasena.isNullOrBlank()){
                        _uiState.update {
                            it.copy(errorContrasena = "La contraseña no puede ir vacío")
                        }
                    }

                    if(_uiState.value.errorNombre == "" && _uiState.value.errorTelefono == ""
                        && _uiState.value.errorCorreo == "" && _uiState.value.errorContrasena == ""){

                        if(_uiState.value.usuarioId == null)
                            usuarioRepository.addUsuario(_uiState.value.toDto())
                        else
                        {
                            usuarioRepository.updateUsuario(
                                _uiState.value.usuarioId ?: 0,
                                _uiState.value.toDto()
                            )
                        }

                        _uiState.update {
                            it.copy(isSuccess = true)
                        }
                    }
                }
            }
            UsuarioUiEvent.Delete -> {
                viewModelScope.launch {
                    usuarioRepository.deleteUsuario(_uiState.value.usuarioId ?: 0)
                }
            }
            UsuarioUiEvent.Refresh -> {
                getUsuarios()
            }
        }
    }

    private fun UsuarioUiState.toDto() = UsuarioDto(
        usuarioId = usuarioId,
        nombre = nombre ?: "",
        contrasena = contrasena ?: "",
        correo = correo ?: "",
        telefono = telefono ?: ""
    )
}