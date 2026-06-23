package com.example.companytime.presentation.screen.calendar

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.usecase.meeting.GetMeetingByPersonIdAndDateUseCase
import com.example.companytime.presentation.screen.component.clearTimeToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val getMeetingByPersonIdAndDateUseCase: GetMeetingByPersonIdAndDateUseCase,
    private val tokenStorage: TokenStorage
): ViewModel() {
    private val _state = MutableStateFlow(CalendarContract.State())
    val state = _state.asStateFlow()

    init {
        loadMeetingsOfSelectedDay(_state.value.selectedDay)
    }

    fun onIntent(intent: CalendarContract.Intent) {
        when(intent) {
            is CalendarContract.Intent.SwitchDay -> loadMeetingsOfSelectedDay(intent.day.clearTimeToDate())
        }
    }

    private fun loadMeetingsOfSelectedDay(date: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val currentPerson = tokenStorage.getUserId()
            getMeetingByPersonIdAndDateUseCase(currentPerson, date)
                .onSuccess { meetings ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            meetingsForSelectedDay = meetings,
                            selectedDay = date
                        )
                    }
                }
                .onFailure { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка получения встреч"
                        )
                    }
                }
        }
    }
}