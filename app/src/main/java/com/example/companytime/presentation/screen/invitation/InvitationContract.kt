package com.example.companytime.presentation.screen.invitation

import com.example.companytime.domain.model.Meeting

sealed interface InvitationContract {
    data class State(
        val invitations: List<Meeting> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val toastMessage: String? = null
    )
    sealed interface Intent {
        data class ClickAcceptButton(val meetingId: Long): Intent
        data class ClickDeclineButton(val meetingId: Long): Intent
    }
}