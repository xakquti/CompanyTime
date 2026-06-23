package com.example.companytime.presentation.screen.profile.editinfo

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.usecase.person.GetPersonByIdUseCase
import com.example.companytime.domain.usecase.person.UpdatePersonUseCase
import com.example.companytime.domain.usecase.person.UploadImageUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditInfoScreenViewModel(
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val tokenStorage: TokenStorage,
    private val getPersonByIdUseCase: GetPersonByIdUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val appContext: Context
): ViewModel() {

    private val _state = MutableStateFlow(EditInfoScreenContract.State())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<EditInfoScreenContract.Action>()
    val actionFlow = _actionFlow.asSharedFlow()

    init {
        loadProfileInfo()
    }

    fun onIntent(intent: EditInfoScreenContract.Intent) {
        when(intent) {
            is EditInfoScreenContract.Intent.ChangeName -> {
                _state.update {
                    it.copy(
                        name = intent.name
                    )
                }
            }
            is EditInfoScreenContract.Intent.ChangePhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = intent.phoneNumber
                    )
                }
            }
            is EditInfoScreenContract.Intent.ClickSaveButton -> save()
            is EditInfoScreenContract.Intent.ChangePhotoUri -> uploadImg(intent.uri)
        }
    }

    private fun uploadImg(uri: Uri) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            uploadImageUseCase(appContext, uri)
                .onSuccess { person ->
                    _state.update {
                        it.copy(
                            photoUrl = person.photoUrl,
                            isLoading = false
                        )
                    }
                }
                .onFailure { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка смены аватара"
                        )
                    }
                }
        }
    }

    private fun save() {
        if(_state.value.name.isBlank() || _state.value.phoneNumber.isBlank()) {
            _state.update {
                it.copy(error = "ФИО и номер телефона не могут быть пустыми")
                return
            }
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val id = tokenStorage.getUserId()
            updatePersonUseCase(
                id = id,
                name = _state.value.name,
                phoneNumber = _state.value.phoneNumber
            )
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _actionFlow.emit(EditInfoScreenContract.Action.NavigateToProfile)
                }
                .onFailure { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка обновления профиля"
                        )
                    }
                }
        }
    }

    private fun loadProfileInfo() {
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
                            photoUrl = person.photoUrl
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Не удалось загрузить профиль"
                        )
                    }
                }
        }
    }
}