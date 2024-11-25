package edu.ucne.proyectofinalaplicada2.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import edu.ucne.proyectofinalaplicada2.data.repository.UsuarioRepository
import edu.ucne.proyectofinalaplicada2.presentation.sign_in.SignInResult
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

    fun onSignInResult(result: SignInResult) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSignInSuccessful = result.data != null,
                    signInError = result.errorMessage,
                    usuarioId = null,
                    nombre = result.data?.userName,
                    telefono = result.data?.phoneNumber,
                    correo = result.data?.email,
                    contrasena = result.data?.password,
                    fotoPerfil = result.data?.profilePictureUrl
                )
            }
            if(usuarioRepository.getUsuarioCorreo(_uiState.value.correo ?: "") == null){
                guardarUsuario()
            }
        }
    }

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
                _uiState.update { it.copy(usuarioId = event.usuarioId) }
            }
            is UsuarioUiEvent.NombreChanged -> {
                _uiState.update { it.copy(nombre = event.nombre) }
            }
            is UsuarioUiEvent.TelefonoChanged -> {
                _uiState.update { it.copy(telefono = formatPhoneNumber(event.telefono)) }
            }
            is UsuarioUiEvent.CorreoChanged -> {
                _uiState.update { it.copy(correo = event.correo) }
            }
            is UsuarioUiEvent.ContrasenaChanged -> {
                _uiState.update { it.copy(contrasena = event.contrasena) }
            }
            is UsuarioUiEvent.ConfirmarContrasenaChanged -> {
                _uiState.update { it.copy(confirmarContrasena = event.confirmarContrasena) }
            }
            is UsuarioUiEvent.SelectedUsuario -> {
                viewModelScope.launch {
                    cargarUsuarioSeleccionado(event.usuarioId)
                }
            }
            is UsuarioUiEvent.IsRefreshingChanged -> {
                _uiState.update {
                    it.copy(isRefreshing = event.isRefreshing)
                }
            }
            UsuarioUiEvent.Register -> {
                viewModelScope.launch {
                    if (validarCampos()) {
                        guardarUsuario()
                        _uiState.update { it.copy(isSuccess = true) }
                    }
                }
            }
            UsuarioUiEvent.Delete -> {
                viewModelScope.launch {
                    usuarioRepository.deleteUsuario(_uiState.value.usuarioId ?: 0)
                }
            }
            UsuarioUiEvent.Login -> TODO()
            UsuarioUiEvent.Refresh -> {
                getUsuarios()
            }
        }
    }

    private suspend fun guardarUsuario() {
        if (_uiState.value.usuarioId == null) {
            usuarioRepository.addUsuario(_uiState.value.toDto())
        } else {
            usuarioRepository.updateUsuario(_uiState.value.usuarioId ?: 0, _uiState.value.toDto())
        }
    }

    private fun validarCampos(): Boolean {
        var isValid = true
        _uiState.update {
            it.copy(
                errorNombre = if (it.nombre.isNullOrBlank()) {
                    isValid = false
                    "El campo nombre no puede estar vacío"
                } else null,
                errorTelefono = if (it.telefono.isNullOrBlank()) {
                    isValid = false
                    "El campo teléfono no puede estar vacío"
                } else if (it.telefono.length != 12) {
                    isValid = false
                    "El campo teléfono debe tener 10 caracteres"
                } else null,
                errorCorreo = if (it.correo.isNullOrBlank()) {
                    isValid = false
                    "El campo correo no puede estar vacío"
                } else if (!isValidEmail(it.correo)) {
                    isValid = false
                    "El campo correo no es válido"
                } else null,
                errorContrasena = if (it.contrasena.isNullOrBlank()) {
                    isValid = false
                    "El campo contraseña no puede estar vacío"
                } else if (it.contrasena.length !in 5..21) {
                    isValid = false
                    "El campo contraseña debe tener al menos 6 caracteres y máximo 20"
                } else null,
                errorConfirmarContrasena = if (it.confirmarContrasena.isNullOrBlank()) {
                    isValid = false
                    "El campo confirmar contraseña no puede estar vacío"
                } else if (it.contrasena != it.confirmarContrasena) {
                    isValid = false
                    "Las contraseñas no coinciden"
                } else null
            )
        }
        return isValid
    }

    private fun cargarUsuarioSeleccionado(usuarioId: Int) = viewModelScope.launch {
        if (usuarioId > 0) {
            val usuario = usuarioRepository.getUsuario(usuarioId)
            _uiState.update {
                it.copy(
                    usuarioId = usuario.usuarioId,
                    nombre = usuario.nombre,
                    telefono = usuario.telefono,
                    correo = usuario.correo,
                    contrasena = usuario.contrasena,
                    fotoPerfil = usuario.fotoPerfil
                )
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        val cleanedNumber = phoneNumber.filter { it.isDigit() }
        return if (cleanedNumber.length == 10) {
            "${cleanedNumber.substring(0, 3)}-${cleanedNumber.substring(3, 6)}-${cleanedNumber.substring(6, 10)}"
        } else {
            phoneNumber
        }
    }


    private fun UsuarioUiState.toDto() = UsuarioDto(
        usuarioId = usuarioId,
        nombre = nombre ?: "",
        contrasena = contrasena ?: "",
        correo = correo ?: "",
        telefono = telefono ?: "",
        fotoPerfil = fotoPerfil
    )
}