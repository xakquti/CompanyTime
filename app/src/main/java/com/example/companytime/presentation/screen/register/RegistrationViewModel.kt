package com.example.companytime.presentation.screen.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companytime.domain.usecase.department.GetAllDepartmentsUseCase
import com.example.companytime.domain.usecase.person.CreatePersonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val createPersonUseCase: CreatePersonUseCase,
    private val getAllDepartmentsUseCase: GetAllDepartmentsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(RegistrationContract.State())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<RegistrationContract.Action>()
    val actionFlow = _actionFlow.asSharedFlow()

    init {
        loadDepartments()
    }


    fun onIntent(intent: RegistrationContract.Intent) {
        when(intent) {
            is RegistrationContract.Intent.ChangeName -> changeName(intent.name)
            is RegistrationContract.Intent.ChangeEmail -> changeEmail(intent.email)
            is RegistrationContract.Intent.ChangePassword -> changePassword(intent.password)
            is RegistrationContract.Intent.ChangePhoneNumber -> changePhoneNumber(intent.phoneNumber)
            is RegistrationContract.Intent.ClickPasswordVisible -> _state.update {
                it.copy(isButtonPasswordVisible = !it.isButtonPasswordVisible)
            }

            is RegistrationContract.Intent.SelectDepartment -> _state.update {
                it.copy(department = intent.department)
            }
            RegistrationContract.Intent.ClickRegisterButton -> clickRegisterButton()
        }
    }

    private fun changeName(name: String) {
        _state.update { current ->
            val newName = name
            current.copy(
                name = newName
            )
        }
    }

    private fun changeEmail(email: String) {
        _state.update { current ->
            val newEmail = email
            current.copy(
                email = newEmail
            )
        }
    }

    private fun changePassword(password: String) {
        _state.update { current ->
            val newPassword = password
            current.copy(
                password = newPassword
            )
        }
    }

    private fun changePhoneNumber(phoneNumber: String){
        _state.update { current ->
            val newPhoneNumber = phoneNumber
            current.copy(
                phoneNumber = newPhoneNumber
            )
        }
    }

    private fun loadDepartments() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            getAllDepartmentsUseCase()
                .onSuccess { names ->
                    _state.update {
                        it.copy(
                            departments = names,
                            isLoading = false
                        )
                    }
                }

                .onFailure { err ->
                    _state.update {
                        it.copy(
                            departments = emptyList(),
                            isLoading = false,
                            error = err.message ?: "Не удалось загрузить список департаментов"
                        )
                    }
                }

        }
    }

    private fun clickRegisterButton() {

        if (_state.value.name.isBlank()){
            _state.update {
                it.copy(
                    errorName = "ФИО не должно быть пустым!"
                )
            }
        } else {
            _state.update {
                it.copy(
                    errorName = null
                )
            }
        }

        if (_state.value.email.isBlank()){
            _state.update {
                it.copy(
                    errorEmail = "Email не может быть пустым!"
                )
            }
        } else {
            _state.update {
                it.copy(
                    errorEmail = null
                )
            }
        }

        if (_state.value.phoneNumber.isBlank()) {
            _state.update {
                it.copy(
                    errorPhoneNumber = "Номер телефона не может быть пустым!"
                )
            }
        } else {
            _state.update {
                it.copy(
                    errorPhoneNumber = null
                )
            }
        }

        if (_state.value.department.isBlank()) {
            _state.update {
                it.copy(
                    errorDepartment = "Департамент не может быть пустым!"
                )
            }
        } else {
            _state.update {
                it.copy(
                    errorDepartment = null
                )
            }
        }

        if (_state.value.password.isBlank()) {
            _state.update {
                it.copy(
                    errorPassword = "Пароль не может быть пустым!"
                )
            }
        } else if (_state.value.password.length < 8){
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

        if (_state.value.name.isBlank() || _state.value.email.isBlank() || _state.value.phoneNumber.isBlank()
            || _state.value.department.isBlank() || _state.value.password.isBlank()) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            createPersonUseCase(
                _state.value.name,
                _state.value.email,
                _state.value.password,
                _state.value.department,
                _state.value.phoneNumber
            )
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    _actionFlow.emit(RegistrationContract.Action.NavigateToApp)
                }

                .onFailure { result ->
                    val e = result.message
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e ?: "Неизвестная ошибка"
                        )
                    }
                }


        }

    }

}