package com.example.companytime.presentation.screen.invitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companytime.domain.usecase.meeting.GetInvitationsUseCase
import com.example.companytime.domain.usecase.meeting.ReplaceStatusUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InvitationViewModel(
    private val getInvitationsUseCase: GetInvitationsUseCase,
    private val replaceStatusUseCase: ReplaceStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(InvitationContract.State())
    val state = _state.asStateFlow()

    init {
        loadInvitations()
    }

    fun onIntent(intent: InvitationContract.Intent) {
        when (intent) {
            is InvitationContract.Intent.ClickAcceptButton -> accept(intent.meetingId)
            is InvitationContract.Intent.ClickDeclineButton -> decline(intent.meetingId)
        }
    }

    private fun loadInvitations() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            getInvitationsUseCase()
                .onSuccess { inv ->
                    _state.update {
                        it.copy(
                            invitations = inv,
                            isLoading = false
                        )
                    }
                }
                .onFailure { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка загрузки приглашений"
                        )
                    }
                }
        }
    }

    private fun accept(meetingId: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            replaceStatusUseCase(meetingId, true)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            toastMessage = "Приглашение принято!!!"
                        )
                    }
                    loadInvitations()
                }
                .onFailure { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка принятия приглашения"
                        )
                    }
                }
        }
    }

    private fun decline(meetingId: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            replaceStatusUseCase(meetingId, false)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            toastMessage = "Приглашение отклонено"
                        )
                    }
                    loadInvitations()
                }
                .onFailure { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка отклонения приглашения"
                        )
                    }
                }
        }
    }
}