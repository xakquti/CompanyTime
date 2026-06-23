package com.example.companytime.data.repository

import com.example.companytime.data.ApiService
import com.example.companytime.data.TokenStorage
import com.example.companytime.data.mapper.toDto
import com.example.companytime.data.mapper.toUi
import com.example.companytime.domain.model.Meeting
import com.example.companytime.domain.repository.MeetingRepository

class MeetingRepositoryImpl(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage
): MeetingRepository {
    override suspend fun getMeetingByPersonId(id: Long): List<Meeting> {
        return apiService.getMeetingByPersonId(id).map {it.toUi()}
    }

    override suspend fun getMeetingByPersonIdAndDate(
        id: Long,
        date: Long
    ): List<Meeting> {
        return apiService.getMeetingByPersonIdAndDate(id, date).map{it.toUi()}
    }

    override suspend fun deleteMeeting(id: Long) {
        return apiService.deleteMeeting(id)
    }

    override suspend fun createMeeting(meeting: Meeting) {
        apiService.createMeeting(meeting.toDto())
    }

    override suspend fun getMeetingById(id: Long): Meeting {
        return apiService.getMeetingById(id).toUi()
    }

    override suspend fun getInvitations(): List<Meeting> {
        val id = tokenStorage.getUserId()
        return apiService.getInvitations(id)
            .map{
                it.toUi()
            }
    }

    override suspend fun replaceStatus(id: Long, status: String) {
        val personId = tokenStorage.getUserId()
        return apiService.replaceStatus(
            id, personId, status
        )
    }
}