package com.example.companytime.presentation.screen.register

sealed interface RegistrationContract {
    data class State (
        val password: String = "",
        val email: String = "",
        val name: String = "",
        val department: String = "",
        val departments: List<String> = emptyList(),
        val phoneNumber: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val isButtonPasswordVisible: Boolean = false,
        val errorPhoneNumber: String? = null,
        val errorEmail: String? = null,
        val errorPassword: String? = null,
        val errorName: String? = null,
        val errorDepartment: String? = null,
    )

    sealed interface Intent {
        data object ClickRegisterButton: Intent
        data class ChangeName(val name: String): Intent
        data class ChangeEmail(val email: String): Intent
        data class ChangePassword(val password: String): Intent
        data class ChangePhoneNumber(val phoneNumber: String): Intent

        data class SelectDepartment(val department: String): Intent
        data object ClickPasswordVisible: Intent
    }

    sealed interface Action{
        data object NavigateToApp: Action

        data object NavigateBack: Action
    }
}