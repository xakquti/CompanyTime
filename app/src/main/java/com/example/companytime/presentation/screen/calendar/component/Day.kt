package com.example.companytime.presentation.screen.calendar.component

import android.annotation.SuppressLint
import android.icu.util.LocaleData
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
@SuppressLint("NewApi")
data class Day(
    val date: LocalDate,
    val isSelected: Boolean = false
) {
    val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.of("ru")).toString()

    val dayMonth = date.dayOfMonth.toString()
}
