package com.example.companytime.domain.model


data class Person(
    val id: Long,
    val name: String,
    val username: String,
    val photoUrl: String? = null,
    val departmentName: String,
    val phoneNumber: String
)