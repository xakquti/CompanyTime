package com.example.companytime.presentation.screen.profile.editinfo

import android.net.Uri
import com.example.companytime.presentation.screen.profile.ProfileContract

sealed interface EditInfoScreenContract {
    data class State(
        val photoUrl: String? = null,
        val name: String = "",
        val phoneNumber: String = "",
//        val department: String = "",
//        val departments: List<String> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Intent {
        data object ClickSaveButton: Intent
        data class ChangeName(val name: String): Intent
        data class ChangePhoneNumber(val phoneNumber: String): Intent
        data class ChangePhotoUri(val uri: Uri): Intent
    }

    sealed interface Action {
        data object NavigateToProfile: Action
    }
}