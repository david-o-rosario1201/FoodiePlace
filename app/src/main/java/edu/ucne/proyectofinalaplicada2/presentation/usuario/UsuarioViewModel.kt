package edu.ucne.proyectofinalaplicada2.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.proyectofinalaplicada2.data.remote.Resource
import edu.ucne.proyectofinalaplicada2.data.remote.dto.UsuarioDto
import edu.ucne.proyectofinalaplicada2.data.repository.AuthRepository
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
    private val usuarioRepository: UsuarioRepository,
    private val authRepository: AuthRepository,
): ViewModel(){
    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUsuarios()
    }

    fun onSignInResult(result: SignInResult) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSignInSuccessful = result.data != null,
                    signInError = result.errorMessage,
                    usuarioId = null,
                    rol = "Client",
                    nombre = result.data?.userName,
                    telefono = result.data?.phoneNumber,
                    correo = result.data?.email,
                    contrasena = result.data?.password,
                    fotoPerfil = result.data?.profilePictureUrl
                )
            }
            val usuario = _uiState.value.usuarios.find { result ->
                result.correo == _uiState.value.correo
            }
            if (usuario == null) {
                usuarioRepository.addUsuario(_uiState.value.toDto())
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

    fun getCurrentUser(){
        viewModelScope.launch {
            val currentUser = authRepository.getUser()
            val usuarioActual = usuarioRepository.getUsuarioByCorreo(currentUser ?: "")

            _uiState.update {
                it.copy(
                    nombre = usuarioActual?.nombre,
                    correo = usuarioActual?.correo,
                    fotoPerfil = usuarioActual?.fotoPerfil
                )
            }
        }
    }

    fun onEvent(event: UsuarioUiEvent) {
        when (event) {
            is UsuarioUiEvent.UsuarioIdChanged -> updateUiState { it.copy(usuarioId = event.usuarioId) }
            is UsuarioUiEvent.NombreChanged -> updateUiState { it.copy(nombre = event.nombre) }
            is UsuarioUiEvent.TelefonoChanged -> updateUiState { it.copy(telefono = formatPhoneNumber(event.telefono)) }
            is UsuarioUiEvent.CorreoChanged -> updateUiState { it.copy(correo = event.correo) }
            is UsuarioUiEvent.ContrasenaChanged -> updateUiState { it.copy(contrasena = event.contrasena) }
            is UsuarioUiEvent.ConfirmarContrasenaChanged -> updateUiState { it.copy(confirmarContrasena = event.confirmarContrasena) }
            is UsuarioUiEvent.SelectedUsuario -> handleSelectedUsuario(event.usuarioId)
            is UsuarioUiEvent.IsRefreshingChanged -> updateUiState { it.copy(isRefreshing = event.isRefreshing) }
            UsuarioUiEvent.Register -> handleRegisterEvent()
            UsuarioUiEvent.Delete -> handleDeleteEvent()
            UsuarioUiEvent.Login -> handleLoginEvent()
            UsuarioUiEvent.Refresh -> getUsuarios()
            UsuarioUiEvent.Logout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    private fun updateUiState(update: (UsuarioUiState) -> UsuarioUiState) {
        _uiState.update(update)
    }

    private fun handleSelectedUsuario(usuarioId: Int) {
        viewModelScope.launch {
            cargarUsuarioSeleccionado(usuarioId)
        }
    }

    private fun handleRegisterEvent() {
        viewModelScope.launch {
            if (validarCampos()) {
                guardarUsuario()
                updateUiState { it.copy(isSuccess = true) }
                createUserWithEmailAndPassword()
            }
        }
    }

    private fun createUserWithEmailAndPassword() {
        viewModelScope.launch {
            authRepository.registerUser(
                _uiState.value.correo.orEmpty(),
                _uiState.value.contrasena.orEmpty()
            ).collect{ result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = true,
                                isSignInSuccessful = true,
                                signInError = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = false,
                                isSignInSuccessful = false,
                                signInError = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleDeleteEvent() {
        viewModelScope.launch {
            usuarioRepository.deleteUsuario(_uiState.value.usuarioId ?: 0)
        }
    }

    private fun handleLoginEvent() {
        viewModelScope.launch {
            authRepository.loginUser(
                _uiState.value.correo.orEmpty(),
                _uiState.value.contrasena.orEmpty()
            ).collect{ result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSignInSuccessful = true,
                                signInError = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSignInSuccessful = false,
                                signInError = result.message
                            )
                        }
                    }
                }
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
        val usuario = _uiState.value.usuarios.find { result ->
            result.nombre == _uiState.value.nombre
                    || result.correo == _uiState.value.correo
                    || result.telefono == _uiState.value.telefono
        }

        var isValid = true
        _uiState.update {
            it.copy(
                errorNombre = when {
                    it.nombre.isNullOrBlank() -> {
                        isValid = false
                        "El campo nombre no puede estar vacío"
                    }
                    usuario?.nombre == it.nombre -> {
                        isValid = false
                        "Ya existe un usuario con ese nombre"
                    }
                    else -> null
                },
                errorTelefono = when {
                    it.telefono.isNullOrBlank() -> {
                        isValid = false
                        "El campo teléfono no puede estar vacío"
                    }
                    it.telefono.length != 12 -> {
                        isValid = false
                        "El campo teléfono debe tener 10 caracteres"
                    }
                    usuario?.telefono == it.telefono -> {
                        isValid = false
                        "Ya existe un usuario con ese teléfono"
                    }
                    else -> null
                },
                errorCorreo = when {
                    it.correo.isNullOrBlank() -> {
                        isValid = false
                        "El campo correo no puede estar vacío"
                    }
                    !isValidEmail(it.correo) -> {
                        isValid = false
                        "El campo correo no es válido"
                    }
                    usuario?.correo == it.correo -> {
                        isValid = false
                        "Ya existe un usuario con ese correo"
                    }
                    else -> null
                },
                errorContrasena = when {
                    it.contrasena.isNullOrBlank() -> {
                        isValid = false
                        "El campo contraseña no puede estar vacío"
                    }
                    it.contrasena.length !in 5..21 -> {
                        isValid = false
                        "El campo contraseña debe tener al menos 6 caracteres y máximo 20"
                    }
                    else -> null
                },
                errorConfirmarContrasena = when {
                    it.confirmarContrasena.isNullOrBlank() -> {
                        isValid = false
                        "El campo confirmar contraseña no puede estar vacío"
                    }
                    it.contrasena != it.confirmarContrasena -> {
                        isValid = false
                        "Las contraseñas no coinciden"
                    }
                    else -> null
                }
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
        rol = rol ?: "",
        nombre = nombre ?: "",
        contrasena = contrasena ?: "",
        correo = correo ?: "",
        telefono = telefono ?: "",
        fotoPerfil = fotoPerfil
    )
}