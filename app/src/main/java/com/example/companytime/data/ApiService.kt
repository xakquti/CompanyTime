package com.example.companytime.data

import com.example.companytime.data.dto.DepartmentDto
import com.example.companytime.data.dto.MeetingDto
import com.example.companytime.data.dto.PageResponse
import com.example.companytime.data.dto.PersonDto
import com.example.companytime.data.dto.PersonRegisterDto
import com.example.companytime.domain.model.Department
import com.example.companytime.domain.model.Meeting
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //person
    @GET("/api/person")
    suspend fun getAllPersons(): List<PersonDto>

    @GET("/api/person/{id}")
    suspend fun getPersonById(@Path("id") id: Long): PersonDto

    @POST("/api/person/register")
    suspend fun createPerson(@Body dto: PersonRegisterDto): PersonDto

    @PATCH("/api/person/{id}")
    suspend fun updatePerson(@Path("id") id: Long, @Body dto: PersonDto): PersonDto

    @GET("/api/person/login")
    suspend fun loginPerson(
        @Header("Authorization") auth: String
    ): PersonDto

    @GET("/api/person/username/{username}")
    suspend fun getPersonByUserName(@Path("username") username: String): PersonDto

    @GET("/api/person/paginated")
    suspend fun getPaginatedPersons(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PageResponse<PersonDto>

    @Multipart
    @PATCH("/api/person/{id}/avatar")
    suspend fun uploadAvatar(@Path("id") id: Long, @Part multipartFile: MultipartBody.Part): PersonDto

    //meeting

    @GET("/api/meeting/person/{personId}")
    suspend fun getMeetingByPersonId(@Path("personId") id: Long): List<MeetingDto>

    @GET("/api/meeting/person/{personId}/date")
    suspend fun getMeetingByPersonIdAndDate(@Path("personId") id: Long,
                                            @Query("date") date: Long): List<MeetingDto>

    @DELETE("/api/meeting/{id}")
    suspend fun deleteMeeting(@Path("id") id: Long)

    @POST("/api/meeting")
    suspend fun createMeeting(@Body dto: MeetingDto)

    @GET("api/meeting/{id}")
    suspend fun getMeetingById(@Path("id") id: Long): MeetingDto

    @GET("api/meeting/person/{personId}/invitations")
    suspend fun getInvitations(@Path("personId") id: Long): List<MeetingDto>

    @PATCH("api/meeting/{id}/person/{personId}/status")
    suspend fun replaceStatus(@Path("id") id: Long, @Path("personId") personId: Long,
                              @Query("status") status: String)


    //department
    @GET("api/department")
    suspend fun getAllDepartments(): List<DepartmentDto>


    @GET("api/department/{id}")
    suspend fun getDepartmentById(@Path("id") id: Long): DepartmentDto

    @GET("api/department/{name}")
    suspend fun getDepartmentByName(@Path("name") name: String): DepartmentDto
}