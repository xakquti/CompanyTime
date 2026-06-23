package com.example.companytime.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.companytime.R

data class BottomNavItem(
    val icon: Int
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Screen.Main.Invitation to BottomNavItem(
        icon = R.drawable.invitation
    ),
    Screen.Main.Calendar to BottomNavItem(
        icon = R.drawable.calendar
    ),
    Screen.Main.Meeting to BottomNavItem(
        icon = R.drawable.meeting
    ),
    Screen.Main.Profile to BottomNavItem(
        icon = R.drawable.profile
    ),
)