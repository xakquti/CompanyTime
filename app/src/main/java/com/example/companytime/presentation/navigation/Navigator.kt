package com.example.companytime.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList

class Navigator(
    startDestination: Screen
) {
    val backStack: SnapshotStateList<Screen> = mutableStateListOf(startDestination)

    fun navigateTo(destination: Screen) {
        backStack.add(destination)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }

    fun goToMain(tab: Screen.Main) {
        backStack.clear()
        backStack.add(tab)
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun switch(tab: Screen.Main) {
        if (backStack.lastOrNull() is Screen.Main) {
            backStack.removeLast()
        }
        backStack.add(tab)
    }

    fun logout() {
        Snapshot.withMutableSnapshot {
            backStack.clear()
            backStack.add(Screen.Start)
        }
    }
}