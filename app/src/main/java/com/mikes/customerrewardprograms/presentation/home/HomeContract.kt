package com.mikes.customerrewardprograms.presentation.home

import com.mikes.customerrewardprograms.domain.Transaction
import com.mikes.customerrewardprograms.domain.User

// MVI State
data class HomeState(
    val user: User? = null,
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

// MVI Intent
sealed interface HomeIntent {
    data object LoadData : HomeIntent
    data object Logout : HomeIntent
    data object ClearError : HomeIntent
}

// MVI Effect
sealed interface HomeEffect {
    data object NavigateToLogin : HomeEffect
    data class ShowMessage(val message: String): HomeEffect
}