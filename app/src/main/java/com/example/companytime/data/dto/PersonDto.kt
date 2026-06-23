package com.example.companytime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val id: Long,
    val name: String,
    val username: String,
    @SerialName("photoUrl")
    val photoUrl: String? = null,
    @SerialName("departmentName")
    val departmentName: String,
    @SerialName("phoneNumber")
    val phoneNumber: String
)