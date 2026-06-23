package com.example.companytime.data.mapper

import com.example.companytime.data.dto.MeetingParticipantDto
import com.example.companytime.data.dto.PersonDto
import com.example.companytime.data.dto.PersonRegisterDto
import com.example.companytime.domain.model.MeetingParticipant
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.model.PersonRegister

private const val BASE_URL = "http://192.168.1.8:8080"
fun PersonDto.toUi(): Person {
    return Person(
        id = id,
        name = name,
        username = username,
        photoUrl = photoUrl?.let { path ->
            if (path.startsWith("http")) return@let path else "$BASE_URL$path"
        },
        departmentName = departmentName,
        phoneNumber = phoneNumber
    )
}

fun Person.toDto(): PersonDto {
    return PersonDto(
        name = name,
        username = username,
        photoUrl = photoUrl?.replace(BASE_URL, ""),
        departmentName = departmentName,
        phoneNumber = phoneNumber,
        id = id
    )
}

fun MeetingParticipantDto.toUi(): MeetingParticipant{
    return MeetingParticipant(
        userId = userId,
        userName = userName,
        status = status
    )
}

fun MeetingParticipant.toDto(): MeetingParticipantDto {
    return MeetingParticipantDto(
        userId = userId,
        userName = userName,
        status = status
    )
}

fun PersonRegister.toDto(): PersonRegisterDto {
    return PersonRegisterDto(
        name = name,
        username = username,
        password = password,
        departmentName = departmentName,
        phoneNumber = phoneNumber
    )
}

fun PersonRegisterDto.toUi(): PersonRegister {
    return PersonRegister(
        name = name,
        username = username,
        password = password,
        departmentName = departmentName,
        phoneNumber = phoneNumber
    )
}

