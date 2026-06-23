package com.example.companytime.presentation.screen.component

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun getDate(mills: Long): String {
    return if (mills == 0L) {
        "Выберите дату"
    } else {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            .format(Date(mills))
    }
}

fun getTime(mills: Long, defaultText: String): String {
    return if (mills == 0L) {
        defaultText
    } else {
        SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }.format(mills)
    }
}

fun Long.clearTimeToDate(): Long {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@clearTimeToDate
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.timeInMillis
}