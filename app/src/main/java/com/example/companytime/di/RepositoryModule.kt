package com.example.companytime.di

import com.example.companytime.data.repository.DepartmentRepositoryImpl
import com.example.companytime.data.repository.MeetingRepositoryImpl
import com.example.companytime.data.repository.PersonRepositoryImpl
import com.example.companytime.domain.repository.DepartmentRepository
import com.example.companytime.domain.repository.MeetingRepository
import com.example.companytime.domain.repository.PersonRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MeetingRepository> {
        MeetingRepositoryImpl(get(), get())
    }

    single<PersonRepository> {
        PersonRepositoryImpl(get())
    }

    single<DepartmentRepository> {
        DepartmentRepositoryImpl(get())
    }
}