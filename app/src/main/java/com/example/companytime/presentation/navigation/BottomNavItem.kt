package com.example.companytime.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.companytime.R

data class BottomNavItem(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val label: String
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Screen.Main.Invitation to BottomNavItem(
        unselectedIcon = R.drawable.inv_outlined,
        label = "Встречи",
        selectedIcon = R.drawable.inv_filled

    ),
    Screen.Main.Calendar to BottomNavItem(
        unselectedIcon = R.drawable.calendar_outlined,
        label = "Календарь",
        selectedIcon = R.drawable.calendar_filled
    ),
    Screen.Main.Meeting to BottomNavItem(
        unselectedIcon = R.drawable.meeting_outlined,
        selectedIcon = R.drawable.meeting_filled,
        label = "Создать"
    ),
    Screen.Main.Profile to BottomNavItem(
        unselectedIcon = R.drawable.profile_outlined,
        selectedIcon = R.drawable.profile_filled,
        label = "Профиль"
    ),
)