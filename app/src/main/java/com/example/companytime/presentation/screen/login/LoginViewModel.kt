package com.example.companytime.presentation.screen.login

import androidx.lifecycle.ViewModel
import com.example.companytime.domain.usecase.person.LoginPersonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginPersonUseCase: LoginPersonUseCase
): ViewModel() {
    private val _state = MutableStateFlow(LoginContract.State())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<LoginContract.Action>()
    val actionFlow = _actionFlow.asSharedFlow()


    fun onIntent(intent: LoginContract.Intent) {
        when(intent) {
            is LoginContract.Intent.ChangeEmail -> changeEmail(intent.email)
            is LoginContract.Intent.ChangePassword -> changePassword(intent.password)
            is LoginContract.Intent.ClickPasswordVisible -> {_state.update {
                it.copy(passwordVisible = !it.passwordVisible)
            }}
            is LoginContract.Intent.ClickLoginButton -> login()
        }
    }

    private fun changeEmail(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    private fun changePassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun login() {
        if (_state.value.email.isBlank()) {
            _state.update {
                it.copy(
                    errorEmail = "Email обязателен"
                )
            }
        } else {
            _state.update {
                it.copy(
                    errorEmail = null
                )
            }
        }

        if (_state.value.password.isBlank()) {
            _state.update {
                it.copy(
                    errorPassword = "Пароль обязателен"
                )
            }
        } else if (_state.value.password.length < 8) {
            _state.update {
                it.copy(
                    errorPassword = "Пароль содержит менее 8 символов"
                )
            }
        } else {
            _state.update {
                it.copy(
                    errorPassword = null
                )
            }
        }

        if (_state.value.email.isBlank() || _state.value.password.isBlank()) {
            return
        }

        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true, error = null)
            }

            loginPersonUseCase(_state.value.email, _state.value.password)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _actionFlow.emit(LoginContract.Action.NavigateToApp)
                }
                .onFailure { ex ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = ex.message ?: "Ошибка регистрации"
                        )
                    }
                }


        }

    }
}