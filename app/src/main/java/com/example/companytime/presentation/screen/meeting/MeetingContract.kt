package com.example.companytime.presentation.screen.meeting

import com.example.companytime.domain.model.MeetingParticipant
import com.example.companytime.domain.model.Person

sealed interface MeetingContract {

    data class State(
        val name: String = "",
        val date: Long = 0,
        val startTime: Long = 0,
        val endTime: Long = 0,
        val participants: Set<MeetingParticipant> = emptySet(),
        val description: String = "",
        val ownerName: String = "",
        val selectedPersonsIds: Set<Long> = emptySet(),
        val personAndIdPersonsSelected: Map<Long, Person> = emptyMap(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Intent {
        data class ChangeName(val name: String) : Intent
        data class ChangeDate(val date: Long) : Intent
        data class ChangeStartTime(val startTime: Long) : Intent
        data class ChangeEndTime(val endTime: Long) : Intent
        data class ChangeDescription(val description: String) : Intent
        data class SelectParticipant(val person: Person) : Intent
        data object ClickCreateButton : Intent
    }

    sealed interface Action {
        data object OnCreate: Action
    }
}