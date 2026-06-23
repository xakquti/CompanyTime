package com.example.companytime.domain.usecase.meeting

import com.example.companytime.domain.model.Meeting
import com.example.companytime.domain.model.MeetingParticipant
import com.example.companytime.domain.repository.MeetingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateMeetingUseCase(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(
        name: String,
        startTime: Long,
        endTime: Long,
        description: String,
        date: Long,
        ownerName: String,
        participants: Set<MeetingParticipant>
    ): Result<Unit> = withContext(Dispatchers.IO){
        runCatching {
            val meeting = Meeting(name = name,
                startTime = startTime,
                endTime = endTime,
                description = description,
                date = date,
                ownerName = ownerName,
                participants = participants
            )
            meetingRepository.createMeeting(meeting)
        }
    }
}