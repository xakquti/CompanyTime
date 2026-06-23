package com.example.companytime.domain.usecase.person

import com.example.companytime.domain.model.Person
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPersonByIdUseCase(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(id: Long): Result<Person> = withContext(Dispatchers.IO) {
        runCatching {
            personRepository.getPersonById(id)
        }
    }
}