package com.example.companytime.di


import com.example.companytime.presentation.screen.calendar.CalendarViewModel
import com.example.companytime.presentation.screen.invitation.InvitationViewModel
import com.example.companytime.presentation.screen.login.LoginViewModel
import com.example.companytime.presentation.screen.meeting.MeetingViewModel
import com.example.companytime.presentation.screen.profile.ProfileViewModel
import com.example.companytime.presentation.screen.profile.editinfo.EditInfoScreenViewModel
import com.example.companytime.presentation.screen.register.RegistrationViewModel
import com.example.companytime.presentation.screen.start.StartViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val viewModelModule = module {

    viewModel {
        StartViewModel(

        )
    }

    viewModel {
        RegistrationViewModel(
            get(),
            get()
        )
    }

    viewModel {
        LoginViewModel(
            get()
        )
    }

    viewModel {
        ProfileViewModel(
            get(),
            get()
        )
    }

    viewModel {
        MeetingViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel {
        InvitationViewModel(
            get(),
            get()
        )
    }

    viewModel {
        CalendarViewModel(
            get(),
            get()
        )
    }

    viewModel {
        EditInfoScreenViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}