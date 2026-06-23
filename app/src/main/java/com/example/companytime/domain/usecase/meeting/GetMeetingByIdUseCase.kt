package com.example.companytime.domain.usecase.meeting

import com.example.companytime.domain.model.Meeting
import com.example.companytime.domain.repository.MeetingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMeetingByIdUseCase(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(id: Long): Result<Meeting> = withContext(Dispatchers.IO) {
        runCatching {
            meetingRepository.getMeetingById(id)
        }
    }
}