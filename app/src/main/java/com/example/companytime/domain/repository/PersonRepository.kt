package com.example.companytime.domain.repository

import androidx.paging.PagingData
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.model.PersonRegister
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface PersonRepository {
    suspend fun getPersonById(id: Long): Person

    suspend fun createPerson(person: PersonRegister): Person

    suspend fun updatePerson(id: Long, person: Person): Person

    suspend fun loginPerson(auth: String): Person

    suspend fun getPersonByUserName(username: String): Person

    fun getPaginatedPersons(page: Int): Flow<PagingData<Person>>

    suspend fun uploadAvatar(id: Long, img: MultipartBody.Part): Person
}