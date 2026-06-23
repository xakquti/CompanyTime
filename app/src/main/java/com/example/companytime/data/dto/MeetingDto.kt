package com.example.companytime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeetingDto(
    val id: Long = 0,
    val name: String,
    @SerialName("startTime")
    val startTime: Long,
    @SerialName("endTime")
    val endTime: Long,
    val date: Long,
    val description: String,
    @SerialName("ownerName")
    val ownerName: String,
    @SerialName("participants")
    val participants: Set<MeetingParticipantDto>
)