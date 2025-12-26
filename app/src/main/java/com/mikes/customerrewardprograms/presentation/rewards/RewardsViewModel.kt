package com.mikes.customerrewardprograms.presentation.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikes.customerrewardprograms.data.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RewardsViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RewardsState())
    val state: StateFlow<RewardsState> = _state.asStateFlow()

    private val _effect = Channel<RewardsEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        observeCurrentUser()
    }

    //Medio verboson este show no?
    fun handleIntent(intent: RewardsIntent) {
        when (intent) {
            is RewardsIntent.StartScanning -> startScanning()
            is RewardsIntent.StopScanning -> stopScanning()
            is RewardsIntent.OnQRScanned -> onQRScanned(intent.qrCode)
            is RewardsIntent.OnPointsChanged -> onPointsChanged(intent.points)
            is RewardsIntent.OnDescriptionChanged -> onDescriptionChanged(intent.description)
            is RewardsIntent.AddPoints -> addPoints()
            is RewardsIntent.ClearMessages -> clearMessages()
            is RewardsIntent.ResetForm -> resetForm()
        }
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            // Que hace este collect?
            userRepository.currentUser.collect { user ->
                _state.update { it.copy(currentUser = user) }
            }
        }
    }

    private fun startScanning() {
        _state.update { it.copy(isScanning = true, error = null) }
    }

    private fun stopScanning() {
        _state.update { it.copy(isScanning = false) }
    }

    private fun onQRScanned(qrCode: String) {
        val targetUser = userRepository.getUsersByQR(qrCode) // por que esto no se lanza desde una corutina?

        if (targetUser == null) {
            _state.update {
                it.copy(
                    isScanning = false,
                    error = "User not found",
                )
            }
            viewModelScope.launch {
                _effect.send(
                    RewardsEffect.ShowError("Usuario no encontrado")
                )
            }
        } else {
            _state.update{
                it.copy(
                    scannedQR = qrCode,
                    targetUser = targetUser,
                    isScanning = false,
                    error = null,
                )
            }
        }
    }

    private fun onPointsChanged(points: String) {
        val filtered = points.filter { it.isDigit() }
        _state.update { it.copy(pointsToAdd = filtered)}
    }

    private fun onDescriptionChanged(description: String) {
        _state.update { it.copy(description = description)}
    }

    private fun addPoints() {
        val currentState = _state.value

        if (currentState.targetUser == null) {
            viewModelScope.launch {
                _effect.send(RewardsEffect.ShowError("Primero Scanea el QR"))
            }
            return
        }

        val points = currentState.pointsToAdd.toIntOrNull()
        if (points == null || points <= 0) {
            viewModelScope.launch {
                //Por que estos effects si se ocupan lanzar por corutina :
                _effect.send(RewardsEffect.ShowError("Cantidad inválida de puntos a redimir"))
            }
            return
        }

        if (currentState.description.isBlank()) {
            viewModelScope.launch {
                _effect.send(RewardsEffect.ShowError("Ingresa una descripción!"))
            }
        }

        // Hasta aquí anunciar la carga!
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            userRepository.addPoints(
                targetUserId = currentState.targetUser.id,
                points = points,
                description = currentState.description,
            )
            .onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        successMessage = "¡Puntos añadidos exitosamente!"
                    )

                }

                _effect.send(RewardsEffect.ShowSuccess("Puntos añadidos!!"))

                resetForm()
            }
            .onFailure { exception ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message,
                    )
                }
                _effect.send(RewardsEffect.ShowError(exception.message ?: "Error al añadir puntos"))
            }
        }
    }

    private fun clearMessages() {
        _state.update {
            it.copy(
                error = null,
                successMessage = null,
            )
        }
    }

    private fun resetForm() {
        _state.update {
            it.copy(
                targetUser = null,
                scannedQR = null,
                pointsToAdd = "",
                description = "",
                error = null,
                successMessage = null,
            )
        }
    }
}