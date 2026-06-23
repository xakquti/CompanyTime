package com.example.companytime.presentation.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.usecase.person.GetPersonByIdUseCase
import com.example.companytime.domain.usecase.person.GetPersonByUserNameUseCase
import com.example.companytime.domain.usecase.person.UpdatePersonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getPersonByIdUseCase: GetPersonByIdUseCase,
    private val tokenStorage: TokenStorage
): ViewModel() {
    private val _state = MutableStateFlow(ProfileContract.State())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<ProfileContract.Action>()
    val actionFlow = _actionFlow.asSharedFlow()

    init {
        loadProfile()
    }

    fun onIntent(intent: ProfileContract.Intent) {
        when(intent) {
            is ProfileContract.Intent.ClickLogoutButton -> logout()
            is ProfileContract.Intent.ClickEditButton -> {
                viewModelScope.launch {
                    _actionFlow.emit(ProfileContract.Action.NavigateToEditScreen)
                }
            }
            is ProfileContract.Intent.LoadProfile -> loadProfile()
        }
    }


    private fun loadProfile() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val userId = tokenStorage.getUserId()
            getPersonByIdUseCase(userId)
                .onSuccess { person ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            name = person.name,
                            phoneNumber = person.phoneNumber,
                            email = person.username,
                            departmentName = person.departmentName,
                            photoUrl = person.photoUrl
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false,
                            error = error.message ?: "Не удалось загрузить профиль"
                        )
                    }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            tokenStorage.clear()
            _actionFlow.emit(ProfileContract.Action.Logout)
        }
    }
}