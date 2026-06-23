package com.example.companytime.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.companytime.data.ApiService
import com.example.companytime.data.PersonPagingDataSource
import com.example.companytime.data.mapper.toDto
import com.example.companytime.data.mapper.toUi
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.model.PersonRegister
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody

class PersonRepositoryImpl(
    private val apiService: ApiService
): PersonRepository {
    override suspend fun getPersonById(id: Long): Person {
        return apiService.getPersonById(id).toUi()
    }

    override suspend fun createPerson(person: PersonRegister): Person {
        return apiService.createPerson(person.toDto()).toUi()
    }

    override suspend fun updatePerson(
        id: Long,
        person: Person
    ): Person {
        return apiService.updatePerson(id, person.toDto()).toUi()
    }

    override suspend fun loginPerson(auth: String): Person {
        return apiService.loginPerson(auth).toUi()
    }

    override suspend fun getPersonByUserName(username: String): Person {
        return apiService.getPersonByUserName(username).toUi()
    }

    override fun getPaginatedPersons(
        page: Int,
    ): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                page
            ),
            pagingSourceFactory = { PersonPagingDataSource(apiService)}
        ).flow.map { pagingData ->
            pagingData.map { dto ->  dto.toUi()  }
        }
    }

    override suspend fun uploadAvatar(
        id: Long,
        img: MultipartBody.Part
    ): Person {
        return apiService.uploadAvatar(id, img).toUi()
    }
}