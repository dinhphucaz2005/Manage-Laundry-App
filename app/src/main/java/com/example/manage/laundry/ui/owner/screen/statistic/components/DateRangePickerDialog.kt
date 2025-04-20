package com.example.manage.laundry.ui.owner.screen.statistic.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePickerDialog(
    onDateRangeSelected: (Pair<LocalDateTime, LocalDateTime>?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    var selectedStartDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedEndDate by remember { mutableStateOf(LocalDate.now()) }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Custom Date Range",
            style = MaterialTheme.typography.titleMedium
        )

        // Start Date Selector
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "From:",
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { showStartDatePicker = true }
            ) {
                Text(selectedStartDate.format(dateFormatter))
            }
        }

        // End Date Selector
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "To:",
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { showEndDatePicker = true }
            ) {
                Text(selectedEndDate.format(dateFormatter))
            }
        }

        // Apply Button
        Button(
            onClick = {
                if (selectedStartDate <= selectedEndDate) {
                    onDateRangeSelected(
                        Pair(
                            selectedStartDate.atStartOfDay(),
                            selectedEndDate.atTime(23, 59, 59)
                        )
                    )
                } else {
                    onDateRangeSelected(null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Range")
        }
    }

    // Date Pickers
    if (showStartDatePicker) {
        val startDateState = rememberDatePickerState(
            initialSelectedDateMillis = selectedStartDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        // Update the start date when confirmed
                        startDateState.selectedDateMillis?.let { millis ->
                            val newDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            selectedStartDate = newDate

                            // Ensure end date isn't before start date
                            if (selectedEndDate.isBefore(newDate)) {
                                selectedEndDate = newDate
                            }
                        }
                        showStartDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showStartDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = startDateState)
        }
    }

    if (showEndDatePicker) {
        val endDateState = rememberDatePickerState(
            initialSelectedDateMillis = selectedEndDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        // Update the end date when confirmed
                        endDateState.selectedDateMillis?.let { millis ->
                            val newDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            selectedEndDate = newDate

                            // Ensure start date isn't after end date
                            if (selectedStartDate.isAfter(newDate)) {
                                selectedStartDate = newDate
                            }
                        }
                        showEndDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showEndDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = endDateState)
        }
    }
}