package com.example.companytime.presentation.screen.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.model.MeetingParticipant
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.usecase.meeting.CreateMeetingUseCase
import com.example.companytime.domain.usecase.person.GetPaginatedPersonsUseCase
import com.example.companytime.domain.usecase.person.GetPersonByIdUseCase
import com.example.companytime.presentation.screen.component.clearTimeToDate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MeetingViewModel(
    private val createMeetingUseCase: CreateMeetingUseCase,
    private val getPaginatedPersonsUseCase: GetPaginatedPersonsUseCase,
    private val tokenStorage: TokenStorage,
    private val getPersonByIdUseCase: GetPersonByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MeetingContract.State())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<MeetingContract.Action>()
    val actionFlow = _actionFlow.asSharedFlow()

    val personPagingFlow = getPaginatedPersonsUseCase(10).cachedIn(viewModelScope)

    init {
        addOwner()
    }

    fun onIntent(intent: MeetingContract.Intent) {
        when (intent) {
            is MeetingContract.Intent.ChangeDate -> _state.update { it.copy(date = intent.date.clearTimeToDate()) }
            is MeetingContract.Intent.ChangeName -> _state.update { it.copy(name = intent.name) }
            is MeetingContract.Intent.ChangeStartTime ->
                _state.update { it.copy(startTime = intent.startTime) }

            is MeetingContract.Intent.ChangeEndTime -> _state.update {
                it.copy(endTime = intent.endTime)
            }

            is MeetingContract.Intent.ChangeDescription -> _state.update { it.copy(description = intent.description) }
            is MeetingContract.Intent.SelectParticipant -> selectParticipant(
                intent.person.id,
                intent.person
            )

            is MeetingContract.Intent.ClickCreateButton -> {
                createMeeting()
            }
        }
    }

    private fun selectParticipant(personId: Long, person: Person) {
        _state.update { current ->
            val updatedParticipantsIds = if (current.selectedPersonsIds.contains(personId)) {
                current.selectedPersonsIds - personId
            } else {
                current.selectedPersonsIds + personId
            }

            val cache = current.personAndIdPersonsSelected.toMutableMap()
            cache[personId] = person

            current.copy(
                selectedPersonsIds = updatedParticipantsIds,
                personAndIdPersonsSelected = cache
            )
        }
    }

    private fun createMeeting() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val ownerId = tokenStorage.getUserId()
            val participants = _state.value.personAndIdPersonsSelected
                .filterKeys { id -> _state.value.selectedPersonsIds.contains(id) && id != ownerId }
                .map { (id, person) ->
                    MeetingParticipant(
                        userId = id,
                        userName = person.username,
                        status = "PENDING"
                    )
                }.toSet()

            val owner = _state.value.personAndIdPersonsSelected[ownerId]
            val participantsWithOwner = participants.toSet() + MeetingParticipant(
                userId = ownerId,
                userName = owner!!.username,
                status = "ACCEPTED"
            )

            createMeetingUseCase(
                name = _state.value.name,
                startTime = _state.value.startTime,
                endTime = _state.value.endTime,
                description = _state.value.description,
                date = _state.value.date,
                ownerName = owner.username,
                participants = participantsWithOwner
            )
                .onSuccess {
                    _state.update { current ->
                        current.copy(
                            isLoading = false,
                        )
                    }
                    _actionFlow.emit(MeetingContract.Action.OnCreate)
                    _state.update {
                        MeetingContract.State()
                    }
                    addOwner()
                }
                .onFailure { err ->
                    _state.update { current ->
                        current.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка создания встречи"
                        )
                    }
                }


        }
    }

    private fun addOwner() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val ownerId = tokenStorage.getUserId()
            getPersonByIdUseCase(ownerId)
                .onSuccess { person ->
                    _state.update {
                        it.copy(
                            selectedPersonsIds = it.selectedPersonsIds + ownerId,
                            personAndIdPersonsSelected = it.personAndIdPersonsSelected + (ownerId to person),
                            isLoading = false
                        )
                    }
                }
                .onFailure { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Ошибка"
                        )
                    }
                }
        }
    }
}