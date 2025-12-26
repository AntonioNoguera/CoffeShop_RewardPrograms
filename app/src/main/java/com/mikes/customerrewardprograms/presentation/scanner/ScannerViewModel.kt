package com.mikes.customerrewardprograms.presentation.scanner

import com.mikes.customerrewardprograms.data.repository.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ScannerState())
    val state: StateFlow<ScannerState> = _state.asStateFlow()

    private val _effect = Channel<ScannerEffect>()
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: ScannerIntent) {
        when (intent) {
            is ScannerIntent.OnQrCodeScanned -> onQRCodeScanned(intent.code)
            is ScannerIntent.StartScanning -> startScanning()
            is ScannerIntent.StopScanning -> stopScanning()
            is ScannerIntent.ClearError -> clearError()
            is ScannerIntent.ManualLogin -> manualLogin(intent.qrCode)
        }
    }

    private fun startScanning() {
        _state.update { it.copy(isScanning = true, error = null) }
    }

    private fun stopScanning() {
        _state.update { it.copy(isScanning = false) }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

    private fun onQRCodeScanned(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, scannedCode = code, isScanning = false) }

            userRepository.loginWithQR(code)
                .onSuccess { user ->
                    _state.update {
                        it.copy(
                            user = user,
                            isLoading = false,
                            error = null
                        )
                    }
                    _effect.send(ScannerEffect.NavigateToHome)
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Error al iniciar sesión"
                        )
                    }
                    _effect.send(ScannerEffect.ShowError(exception.message ?: "Error desconocido"))
                }
        }
    }

    private fun manualLogin(qrCode: String) {
        if (qrCode.isBlank()) {
            _state.update { it.copy(error = "Ingresa un código QR válido") }
            return
        }
        onQRCodeScanned(qrCode)
    }
}