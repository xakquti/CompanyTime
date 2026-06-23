package com.example.companytime.domain.model

import kotlinx.serialization.SerialName

data class MeetingParticipant(
    val userId: Long,
    val userName: String,
    val status: String
)