package com.example.companytime.presentation.screen.start

import com.example.companytime.presentation.screen.register.RegistrationContract

sealed interface StartContract {
    class State

    sealed interface Intent {
        data object ClickRegisterButton: Intent
        data object ClickLoginButton: Intent
    }

    sealed interface Action {
        data object NavigateRegister: Action

        data object NavigateLogin: Action
    }
}