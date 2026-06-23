package com.example.companytime.presentation.navigation

import androidx.navigation3.runtime.NavKey
import com.example.companytime.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen: NavKey {

    @Serializable
    data object Start: Screen

    @Serializable
    data object Registration: Screen

    @Serializable
    data object Login: Screen

    @Serializable
    sealed class Main(): Screen {
        @Serializable
        data object Profile: Main()

        @Serializable
        data object EditProfile: Main()

        @Serializable
        data object Calendar: Main()

        @Serializable
        data object Invitation: Main()

        @Serializable
        data object Meeting: Main()
    }
}