package com.example.companytime.domain.usecase.person

import com.example.companytime.domain.model.Person
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPersonByUserNameUseCase(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(username: String): Result<Person> = withContext(Dispatchers.IO) {
        runCatching {
            personRepository.getPersonByUserName(username)
        }
    }
}