package com.example.companytime.data.repository

import com.example.companytime.data.ApiService
import com.example.companytime.data.mapper.toUi
import com.example.companytime.domain.model.Department
import com.example.companytime.domain.repository.DepartmentRepository

class DepartmentRepositoryImpl(
    private val apiService: ApiService
): DepartmentRepository {

    override suspend fun getAllDepartments(): List<Department> {
        return apiService.getAllDepartments().map {
            it.toUi()
        }
    }

    override suspend fun getDepartmentById(id: Long): Department {
        return apiService.getDepartmentById(id).toUi()
    }

    override suspend fun getDepartmentByName(name: String): Department {
        return apiService.getDepartmentByName(name).toUi()
    }
}