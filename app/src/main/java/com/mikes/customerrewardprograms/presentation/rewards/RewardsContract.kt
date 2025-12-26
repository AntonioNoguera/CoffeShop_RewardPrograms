package com.mikes.customerrewardprograms.presentation.rewards

import com.mikes.customerrewardprograms.domain.User

// MVI State
data class RewardsState(
    val currentUser: User? = null,
    val targetUser: User? = null,
    val scannedQR: String? = null,
    val pointsToAdd: String = "",
    val description: String = "",
    val isScanning: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
)

// MVI Intent (Ta peculiar como se definen estos intents)
sealed interface RewardsIntent {
    data object StartScanning : RewardsIntent
    data object StopScanning : RewardsIntent
    data class OnQRScanned(val qrCode: String) : RewardsIntent
    data class OnPointsChanged(val points: String) : RewardsIntent
    data class OnDescriptionChanged(val description: String) : RewardsIntent
    data object AddPoints : RewardsIntent
    data object ClearMessages : RewardsIntent
    data object ResetForm : RewardsIntent
}

// MVI Effect
sealed interface RewardsEffect {
    data class ShowSuccess(val message: String) : RewardsEffect
    data class ShowError(val message: String) : RewardsEffect
}