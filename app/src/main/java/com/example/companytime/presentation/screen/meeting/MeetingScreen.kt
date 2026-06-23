package com.example.companytime.presentation.screen.meeting

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.companytime.presentation.screen.component.getDate
import com.example.companytime.presentation.screen.component.getTime
import com.example.companytime.presentation.screen.meeting.component.MeetingTextField
import com.example.companytime.presentation.screen.meeting.component.ParticipantTextFiled
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@SuppressLint("SimpleDateFormat", "NewApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MeetingScreen(
    viewModel: MeetingViewModel = koinViewModel(),
    navigateToCalendar: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val clockStateStart = rememberUseCaseState()
    val clockStateEnd = rememberUseCaseState()
    val calendarState = rememberUseCaseState()

    val lazyPagingItems = viewModel.personPagingFlow.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        lazyPagingItems.retry()
        viewModel.actionFlow.collect {
            when(it) {
                is MeetingContract.Action.OnCreate -> navigateToCalendar()
            }
        }
    }

    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(
                horizontal = 16.dp
            )
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(text = "Создайте встречу", color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item{
            Column(
                Modifier
                    .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(20.dp)
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    MeetingTextField(
                        value = state.name,
                        onValueChange = { viewModel.onIntent(MeetingContract.Intent.ChangeName(it)) },
                        label = "Название",
                        hint = "Введите название",
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)

                    MeetingTextField(
                        value = state.description,
                        onValueChange = { viewModel.onIntent(MeetingContract.Intent.ChangeDescription(it)) },
                        label = "Описание",
                        hint = "Введите описание",
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)

                    Column(Modifier.fillMaxWidth()) {
                        Text("Дата",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        TextButton(
                            onClick = { calendarState.show() },
                            modifier = Modifier.fillMaxWidth().heightIn(56.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(text = getDate(state.date),
                                    color = if (state.date == 0L) MaterialTheme.colorScheme.onSurfaceVariant else
                                        MaterialTheme.colorScheme.onSurface,
                                    style = if (state.date == 0L) MaterialTheme.typography.titleSmall
                                    else MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }

                    CalendarDialog(
                        state = calendarState,
                        selection = CalendarSelection.Date { date ->
                            val zone = ZoneId.systemDefault()
                            val midnightMills = date.atStartOfDay(zone).toInstant().toEpochMilli()
                            viewModel.onIntent(MeetingContract.Intent.ChangeDate(
                                midnightMills
                            ))
                        }
                    )
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)

                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                        Text("Время начала",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        TextButton(
                            onClick = { clockStateStart.show() },
                            modifier = Modifier.fillMaxWidth().heightIn(56.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(text = getTime(state.startTime, "Выберите время начала"),
                                    color = if (state.startTime == 0L) MaterialTheme.colorScheme.onSurfaceVariant else
                                        MaterialTheme.colorScheme.onSurface,
                                    style = if (state.startTime == 0L) MaterialTheme.typography.titleSmall
                                    else MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }

                    ClockDialog(
                        state = clockStateStart,
                        selection = ClockSelection.HoursMinutes { hours, minutes ->
                            viewModel.onIntent(MeetingContract.Intent.ChangeStartTime(
                                (hours.hours + minutes.minutes).inWholeMilliseconds
                            ))
                        },
                        config = ClockConfig(
                            is24HourFormat = true
                        )
                    )
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)

                    Column(Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start) {
                        Text("Время окончания",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        TextButton(
                            onClick = { clockStateEnd.show()},
                            modifier = Modifier.fillMaxWidth().heightIn(56.dp),
                        ) {
                            Row (modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start) {
                                Text(text = getTime(state.endTime, "Выберите время окончания"),
                                    color = if (state.endTime == 0L) MaterialTheme.colorScheme.onSurfaceVariant else
                                        MaterialTheme.colorScheme.onSurface,
                                    style = if (state.endTime == 0L) MaterialTheme.typography.titleSmall
                                    else MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }

                    ClockDialog(
                        state = clockStateEnd,
                        selection = ClockSelection.HoursMinutes { hours, minutes ->
                            viewModel.onIntent(MeetingContract.Intent.ChangeEndTime(
                                (hours.hours + minutes.minutes).inWholeMilliseconds
                            ))
                        }
                        ,config = ClockConfig(
                            is24HourFormat = true
                        )
                    )
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)

                    if (state.isLoading) {
                        ContainedLoadingIndicator(
                            modifier = Modifier.size(32.dp).align(Alignment.CenterHorizontally),
                            containerColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                    } else {
                        ParticipantTextFiled(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            persons = lazyPagingItems,
                            label = "Участники",
                            hint = "Выберите участников",
                            selectedIds = state.selectedPersonsIds,
                            onSelectPerson = { person ->
                                viewModel.onIntent(
                                    MeetingContract
                                        .Intent.SelectParticipant(person)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            cache = state.personAndIdPersonsSelected,
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = {
                    viewModel.onIntent(MeetingContract.Intent.ClickCreateButton)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Создать встречу",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
