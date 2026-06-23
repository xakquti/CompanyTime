package com.example.companytime.domain.usecase.meeting

import com.example.companytime.domain.repository.MeetingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteMeetingUseCase(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(id: Long) = withContext(Dispatchers.IO) {
        runCatching {
            meetingRepository.deleteMeeting(id)
        }
    }
}