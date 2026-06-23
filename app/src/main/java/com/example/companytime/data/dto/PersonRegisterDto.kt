package com.example.companytime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonRegisterDto (
    val name: String,
    val username: String,
    val password: String,
    @SerialName("department_name")
    val departmentName: String,
    @SerialName("phone_number")
    val phoneNumber: String
)