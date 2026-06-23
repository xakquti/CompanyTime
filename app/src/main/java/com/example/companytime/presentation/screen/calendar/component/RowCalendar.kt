package com.example.companytime.presentation.screen.calendar.component

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

const val DAYS_TO_LOAD = 30L
const val DAYS_COUNT = 90

@SuppressLint("NewApi")
@Composable
fun RowCalendar(
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
    modifier: Modifier
) {
    val today = LocalDate.now()
    val days = remember {
        generateDays(startDate = today.minusDays(DAYS_TO_LOAD))
    }

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        val idx = days.indexOfFirst { it.date == today }
        if (idx != -1){
            listState.scrollToItem(idx)
        }
    }

    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = listState) {
        itemsIndexed(items = days, key = {idx, day -> day.date.toString()}) { idx, day ->
            DayItem(
                day = day,
                isSelected = day.date == selectedDate,
                onClick = {
                    onSelectedDate(day.date)
                }
            )
        }
    }
}

@Composable
fun DayItem(
    day: Day,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.height(94.dp).width(64.dp)
            .clip(RoundedCornerShape(30.dp))
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable{
                onClick()
            },
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.dayName,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )

        Box(
            modifier = Modifier.size(48.dp).clip(CircleShape)
                .background(if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.tertiary)
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.dayMonth,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else
                    MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@SuppressLint("NewApi")
private fun generateDays(startDate: LocalDate): List<Day> {
    return generateSequence(startDate) { it.plusDays(1) }
        .map{
            Day(it)
        }.take(DAYS_COUNT).toList()
}