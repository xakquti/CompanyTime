package com.example.companytime.domain.usecase.meeting

import com.example.companytime.domain.repository.MeetingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReplaceStatusUseCase(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(id: Long, status: Boolean): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val stausName = if (status) "ACCEPTED" else "DECLINED"
                meetingRepository.replaceStatus(id, stausName)
            }
        }
}