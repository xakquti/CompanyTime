package com.example.companytime.presentation.screen.login

import com.example.companytime.presentation.screen.register.RegistrationContract

sealed interface LoginContract {
    data class State (
        val password: String = "",
        val email: String = "",
        val passwordVisible: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null,
        val errorEmail: String? = null,
        val errorPassword: String? = null
    )

    sealed interface Intent {
        data class ChangePassword(val password: String): Intent
        data class ChangeEmail(val email: String): Intent
        data object ClickPasswordVisible: Intent
        data object ClickLoginButton: Intent
    }

    sealed interface Action {
        data object NavigateToApp: Action

        data object NavigateBack: Action
    }
}