package com.example.companytime.domain.repository

import com.example.companytime.domain.model.Meeting

interface MeetingRepository {
    suspend fun getMeetingByPersonId(id: Long): List<Meeting>

    suspend fun getMeetingByPersonIdAndDate(id: Long, date: Long): List<Meeting>

    suspend fun deleteMeeting(id: Long)

    suspend fun createMeeting(meeting: Meeting)

    suspend fun getMeetingById(id: Long): Meeting
    suspend fun getInvitations(): List<Meeting>
    suspend fun replaceStatus(id: Long, status: String)
}