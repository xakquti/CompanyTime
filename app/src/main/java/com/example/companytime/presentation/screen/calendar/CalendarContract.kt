package com.example.companytime.presentation.screen.calendar

import com.example.companytime.domain.model.Meeting

sealed interface CalendarContract {
    data class State(
        val selectedDay: Long = System.currentTimeMillis(),
        val meetingsForSelectedDay: List<Meeting> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Intent {
        data class SwitchDay(val day: Long): Intent
    }
}