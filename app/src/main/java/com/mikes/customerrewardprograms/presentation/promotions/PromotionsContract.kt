package com.mikes.customerrewardprograms.presentation.promotions

import com.mikes.customerrewardprograms.domain.Promotion


// MVI State
data class PromotionsState(
    val promotion: List<Promotion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

// MVI Intent
sealed interface PromotionsIntent {
    data object LoadPromotions : PromotionsIntent
    data object Retry: PromotionsIntent
}

// MVI Effect
sealed interface PromotionsEffect {
    data class ShowMessage(val message: String): PromotionsEffect
}