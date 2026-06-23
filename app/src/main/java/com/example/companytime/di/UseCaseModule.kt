package com.example.companytime.di

import com.example.companytime.domain.usecase.department.GetAllDepartmentsUseCase
import com.example.companytime.domain.usecase.meeting.CreateMeetingUseCase
import com.example.companytime.domain.usecase.meeting.DeleteMeetingUseCase
import com.example.companytime.domain.usecase.meeting.GetInvitationsUseCase
import com.example.companytime.domain.usecase.meeting.GetMeetingByIdUseCase
import com.example.companytime.domain.usecase.meeting.GetMeetingByPersonIdAndDateUseCase
import com.example.companytime.domain.usecase.meeting.GetMeetingByPersonIdUseCase
import com.example.companytime.domain.usecase.meeting.ReplaceStatusUseCase
import com.example.companytime.domain.usecase.person.CreatePersonUseCase
import com.example.companytime.domain.usecase.person.GetPaginatedPersonsUseCase
import com.example.companytime.domain.usecase.person.GetPersonByIdUseCase
import com.example.companytime.domain.usecase.person.GetPersonByUserNameUseCase
import com.example.companytime.domain.usecase.person.LoginPersonUseCase
import com.example.companytime.domain.usecase.person.UpdatePersonUseCase
import com.example.companytime.domain.usecase.person.UploadImageUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetAllDepartmentsUseCase(get()) }

    factory { CreatePersonUseCase(get(), get()) }

    factory { GetPaginatedPersonsUseCase(get(), get()) }

    factory { GetPersonByIdUseCase(get()) }

    factory { GetPersonByUserNameUseCase(get()) }

    factory { LoginPersonUseCase(get(), get()) }

    factory { UpdatePersonUseCase(get()) }

    factory { CreateMeetingUseCase(get()) }

    factory { DeleteMeetingUseCase(get()) }

    factory { GetMeetingByIdUseCase(get()) }

    factory { GetMeetingByPersonIdAndDateUseCase(get()) }

    factory { GetMeetingByPersonIdUseCase(get()) }

    factory { ReplaceStatusUseCase(get()) }

    factory { GetInvitationsUseCase(get()) }

    factory { UploadImageUseCase(get(), get()) }
}