package com.example.companytime.data.mapper

import com.example.companytime.data.dto.MeetingDto
import com.example.companytime.domain.model.Meeting
import com.example.companytime.domain.model.MeetingParticipant

fun MeetingDto.toUi(): Meeting {
    return Meeting(
        id = id,
        name = name,
        startTime = startTime,
        endTime = endTime,
        date = date,
        description = description,
        ownerName = ownerName,
        participants = participants.map{it.toUi()}.toSet()
    )
}

fun Meeting.toDto(): MeetingDto {
    return MeetingDto(
        id = id,
        name = name,
        startTime = startTime,
        endTime = endTime,
        date = date,
        description = description,
        ownerName = ownerName,
        participants = participants.map {
            it.toDto()
        }.toSet()
    )
}