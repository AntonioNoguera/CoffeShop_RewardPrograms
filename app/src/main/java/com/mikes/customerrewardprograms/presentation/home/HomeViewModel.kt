package com.mikes.customerrewardprograms.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikes.customerrewardprograms.data.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    //Channel?
    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        observerUser()
        observeTransactions()
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadData()
            is HomeIntent.Logout -> logout()
            is HomeIntent.ClearError -> clearError()
        }
    }

    private fun observerUser() {
        viewModelScope.launch {
            userRepository.currentUser.collect { user ->
                _state.update { it.copy(user = user) }
            }
        }
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            userRepository.transactions.collect { transactions ->
                _state.update { it.copy(transactions = transactions) }
            }
        }
    }

    private fun loadData() {
        _state.update { it.copy(isLoading = true)}
        // Mocking data fetching loading
        _state.update { it.copy(isLoading = false) }
    }

    private fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _effect.send(HomeEffect.NavigateToLogin)
        }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

}