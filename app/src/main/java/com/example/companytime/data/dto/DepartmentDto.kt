package com.example.companytime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DepartmentDto(
    val id: Long,
    val name: String
)