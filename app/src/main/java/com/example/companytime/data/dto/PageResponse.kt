package com.example.companytime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageResponse<T>(
    val content: List<T>,
    val last: Boolean,
    @SerialName("totalPages")
    val totalPages: Long
)