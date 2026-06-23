package com.example.companytime.domain.model


data class Meeting(
    val id: Long = 0,
    val name: String,
    val startTime: Long,
    val endTime: Long,
    val date: Long,
    val description: String,
    val ownerName: String,
    val participants: Set<MeetingParticipant>
)