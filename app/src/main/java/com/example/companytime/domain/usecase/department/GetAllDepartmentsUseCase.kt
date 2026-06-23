package com.example.companytime.domain.usecase.department

import com.example.companytime.domain.model.Department
import com.example.companytime.domain.repository.DepartmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllDepartmentsUseCase(
    private val departmentRepository: DepartmentRepository
) {
    suspend operator fun invoke(): Result<List<String>> = withContext(Dispatchers.IO) {
        runCatching {
            departmentRepository.getAllDepartments().map {
                it.name
            }
        }
    }
}