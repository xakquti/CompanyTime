package com.example.companytime.di

import android.annotation.SuppressLint
import android.util.Log
import com.example.companytime.presentation.navigation.Navigator
import com.example.companytime.presentation.navigation.Screen
import com.example.companytime.presentation.screen.calendar.CalendarScreen
import com.example.companytime.presentation.screen.invitation.InvitationScreen
import com.example.companytime.presentation.screen.login.LoginScreen
import com.example.companytime.presentation.screen.meeting.MeetingScreen
import com.example.companytime.presentation.screen.profile.ProfileScreen
import com.example.companytime.presentation.screen.profile.editinfo.EditInfoScreen
import com.example.companytime.presentation.screen.register.RegistrationScreen
import com.example.companytime.presentation.screen.start.StartScreen
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@SuppressLint("NewApi")
@OptIn(KoinExperimentalAPI::class)
val navigationModule = module {

    single { Navigator(startDestination = Screen.Start) }

    navigation<Screen.Start> {
        StartScreen(
            onRegisterClick = {
                get<Navigator>().navigateTo(Screen.Registration)
            },

            onLoginClick = {
                get<Navigator>().navigateTo(Screen.Login)
            }
        )
    }

    navigation<Screen.Registration> {
        RegistrationScreen(
            navigateToApp = {
                get<Navigator>().goToMain(Screen.Main.Profile)
            },

            navigateBack = {
                get<Navigator>().goBack()
            }
        )
    }

    navigation<Screen.Login> {
        LoginScreen(
            navigateToApp = {
                get<Navigator>().navigateTo(Screen.Main.Profile)
            },

            navigateBack = {
                get<Navigator>().goBack()
            }
        )
    }

    navigation<Screen.Main.Invitation> {
        InvitationScreen(

        )
    }

    navigation<Screen.Main.Calendar> {
        CalendarScreen()
    }

    navigation<Screen.Main.Meeting> {
        MeetingScreen(
            navigateToCalendar = {
                get<Navigator>().navigateTo(Screen.Main.Calendar)
            }
        )
    }

    navigation<Screen.Main.Profile> {
        ProfileScreen(
            onLogout = {
                get<Navigator>().logout()
            },
            navigateToEditScreen = {
                get<Navigator>().navigateTo(Screen.Main.EditProfile)
            }
        )
    }

    navigation<Screen.Main.EditProfile> {
        EditInfoScreen(
            navigateToProfile = {
                get<Navigator>().navigateTo(Screen.Main.Profile)
            }
        )
    }

}