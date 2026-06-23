package com.example.companytime.presentation.screen.profile

sealed interface ProfileContract {
    data class State(
        val photoUrl: String? = null,
        val name: String = "",
        val phoneNumber: String = "",
        val email: String = "",
        val departmentName: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Intent {
        data object ClickLogoutButton : Intent
        data object ClickEditButton: Intent
        data object LoadProfile: Intent
    }

    sealed interface Action{
        data object Logout: Action
        data object NavigateToEditScreen: Action
    }
}