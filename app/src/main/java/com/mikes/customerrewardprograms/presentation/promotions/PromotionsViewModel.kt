package com.mikes.customerrewardprograms.presentation.promotions

import androidx.compose.ui.window.isPopupLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikes.customerrewardprograms.data.repository.PromotionRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PromotionsViewModel (
    private val promotionRepository: PromotionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PromotionsState())
    val state: StateFlow<PromotionsState> = _state.asStateFlow()

    private val _effect = Channel<PromotionsEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleIntent(PromotionsIntent.LoadPromotions)
    }

    fun handleIntent(intent: PromotionsIntent) {
        when (intent) {
            is PromotionsIntent.LoadPromotions -> loadPromotions()
            is PromotionsIntent.Retry -> loadPromotions()
        }
    }

    private fun loadPromotions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            //wat?
            promotionRepository.getPromotions()
                .catch{ exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Problemas al cargar las promociones",
                        )
                    }
                }
                .collect { promotions ->
                    _state.update {
                        it.copy(
                            promotion = promotions,
                            isLoading = false,
                            error = null,
                        )
                    }
                }
        }
    }
}