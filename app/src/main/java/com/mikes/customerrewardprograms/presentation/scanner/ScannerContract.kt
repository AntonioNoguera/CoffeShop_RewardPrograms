package com.mikes.customerrewardprograms.presentation.scanner

import com.mikes.customerrewardprograms.domain.User

// MVI State
data class ScannerState(
    val isScanning: Boolean = false,
    val scannedCode: String? = null,
    val user: User? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)

// MVI Intent
sealed interface ScannerIntent {
    data class OnQrCodeScanned(val code: String) : ScannerIntent
    data object StartScanning: ScannerIntent
    data object StopScanning: ScannerIntent
    data object ClearError: ScannerIntent
    data class ManualLogin(val qrCode: String) : ScannerIntent
}

// MVI Effect (one-time events)
sealed interface ScannerEffect {
    data object NavigateToHome : ScannerEffect
    data class ShowError(val message: String) : ScannerEffect
}