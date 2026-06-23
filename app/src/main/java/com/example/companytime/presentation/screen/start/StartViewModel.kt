package com.example.companytime.presentation.screen.start

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartViewModel(

): ViewModel() {

    private val _state = MutableStateFlow(StartContract.State())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<StartContract.Action>()

    val actionFlow = _actionFlow.asSharedFlow()

    fun onIntent(intent: StartContract.Intent) {
        when(intent) {
            is StartContract.Intent.ClickRegisterButton -> { viewModelScope.launch {
                _actionFlow.emit(StartContract.Action.NavigateRegister)
            }
            }
            is StartContract.Intent.ClickLoginButton -> {
                viewModelScope.launch {
                    _actionFlow.emit(StartContract.Action.NavigateLogin)
                }
            }
        }
    }
}