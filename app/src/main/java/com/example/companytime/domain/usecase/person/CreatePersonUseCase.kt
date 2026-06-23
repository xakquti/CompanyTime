package com.example.companytime.domain.usecase.person

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.model.PersonRegister
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreatePersonUseCase(
    private val personRepository: PersonRepository,
    private val tokenStorage: TokenStorage
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        departmentName: String,
        phoneNumber: String
    ): Result<Person> = withContext(Dispatchers.IO) {
        runCatching {
            val person = PersonRegister(name, email, password, departmentName,
                phoneNumber)

            val personRequest = personRepository.createPerson(person)

            val credentials = "$email:$password"
            val encodedToken = Base64.getEncoder().encodeToString(credentials.toByteArray())
            tokenStorage.saveToken(encodedToken)
            tokenStorage.saveUserId(personRequest.id)

            personRequest
        }
    }
}