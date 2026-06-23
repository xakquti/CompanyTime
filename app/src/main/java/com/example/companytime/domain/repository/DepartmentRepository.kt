package com.example.companytime.domain.repository

import com.example.companytime.domain.model.Department

interface DepartmentRepository {

    suspend fun getAllDepartments(): List<Department>

    suspend fun getDepartmentById(id: Long): Department

    suspend fun getDepartmentByName(name: String): Department
}