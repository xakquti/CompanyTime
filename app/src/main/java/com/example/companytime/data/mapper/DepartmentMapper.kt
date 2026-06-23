package com.example.companytime.data.mapper

import com.example.companytime.data.dto.DepartmentDto
import com.example.companytime.domain.model.Department

fun Department.toDto(): DepartmentDto {
    return DepartmentDto(
        id = id,
        name = name
    )
}

fun DepartmentDto.toUi(): Department {
    return Department(
        id = id,
        name = name
    )
}