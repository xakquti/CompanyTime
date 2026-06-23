package com.example.companytime.presentation.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.companytime.R
import com.example.companytime.presentation.screen.calendar.component.MeetingItem
import com.example.companytime.presentation.screen.calendar.component.RowCalendar
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val calendarState = rememberUseCaseState()
    var selectedDay by remember { mutableStateOf(LocalDate.now()) }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        .padding(top = 24.dp)) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.panda_calendar),
            contentDescription = "",
            modifier = Modifier.size(140.dp).align(Alignment.End),
            )
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Расписание", color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                modifier = Modifier
                    .height(56.dp).width(160.dp).clip(RoundedCornerShape(20.dp))
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {calendarState.show()},
                    modifier = Modifier,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.calendar_icon),
                        contentDescription = "calendar",
                        modifier = Modifier.size(56.dp)
                            .background(MaterialTheme.colorScheme.tertiary)
                            .padding(2.dp).clip(CircleShape).padding(2.dp),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
                Text(
                    "Календарь",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            RowCalendar(
                modifier = Modifier.fillMaxWidth(),
                selectedDate = selectedDay,
                onSelectedDate = { it ->
                    selectedDay = it
                    val zoneId = ZoneId.systemDefault()
                    val midnightEpochMillis = selectedDay.atStartOfDay(zoneId).toInstant().toEpochMilli()
                    viewModel.onIntent(CalendarContract.Intent.SwitchDay(midnightEpochMillis))
                }
            )

            if (state.isLoading) {
                ContainedLoadingIndicator(
                    modifier = Modifier.size(32.dp).align(Alignment.CenterHorizontally),
                    containerColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.meetingsForSelectedDay) { meeting ->
                        MeetingItem(
                            meeting
                        )
                    }
                }
            }
        }

    }

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date {
        }
    )
}