package com.example.companytime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeetingParticipantDto(
    @SerialName("userId")
    val userId: Long,
    @SerialName("userName")
    val userName: String,
    val status: String
)