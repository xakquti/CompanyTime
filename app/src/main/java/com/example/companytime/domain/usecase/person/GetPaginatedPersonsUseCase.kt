package com.example.companytime.domain.usecase.person

import androidx.paging.PagingData
import androidx.paging.filter
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetPaginatedPersonsUseCase(
    private val personRepository: PersonRepository,
    private val tokenStorage: TokenStorage
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Person>> {
        val currentId = tokenStorage.getUserId()
        return personRepository.getPaginatedPersons(pageSize).map {
            it.filter { person ->
                person.id != currentId
            }
        }
    }
}