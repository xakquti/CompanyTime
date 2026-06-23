package com.example.companytime.domain.usecase.person

import com.example.companytime.domain.model.Department
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdatePersonUseCase(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String,
        phoneNumber: String
        ): Result<Person> = withContext(Dispatchers.IO){
        runCatching {
            val curPer = personRepository.getPersonById(id)
            val person = curPer.copy(
                id = id,
                name = name,
                phoneNumber = phoneNumber
            )
            personRepository.updatePerson(id, person)
        }
    }
}