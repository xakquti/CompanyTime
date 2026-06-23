package com.example.companytime.domain.usecase.person

import android.util.Base64
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginPersonUseCase(
    private val personRepository: PersonRepository,
    private val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(email: String, password: String):
            Result<Person> = withContext(Dispatchers.IO){
        runCatching {

            val auth = "Basic ${Base64.encodeToString("$email:$password".toByteArray(),
                Base64.NO_WRAP)}"

            val person = personRepository.loginPerson(auth)

            tokenStorage.saveToken("${Base64.encodeToString("$email:$password".toByteArray(),
                Base64.NO_WRAP)}")

            tokenStorage.saveUserId(person.id)

            person
        }
    }
}