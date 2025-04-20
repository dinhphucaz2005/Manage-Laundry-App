package com.example.manage.laundry.ui.owner.screen.statistic

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.owner.screen.statistic.components.DateRangePickerDialog
import com.example.manage.laundry.ui.owner.screen.statistic.components.DateRangeSelector
import com.example.manage.laundry.ui.owner.screen.statistic.components.ServiceStatisticsContent
import com.example.manage.laundry.ui.owner.screen.statistic.components.StatisticsHomeContent
import com.example.manage.laundry.ui.owner.screen.statistic.components.StatisticsInRangeContent
import com.example.manage.laundry.ui.owner.screen.statistic.model.DateTab
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
@Preview(showBackground = true)
@Composable
private fun ManageStatisticsScreenPreview() {
    ManageLaundryAppTheme {
        StatisticsScreen(statisticsViewModel = fakeViewModel<StatisticsViewModel>())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsScreen(
    statisticsViewModel: StatisticsViewModel
) {
    val statisticsResponse by statisticsViewModel.statisticsResponse.collectAsState()
    val statisticsInRangeResponse by statisticsViewModel.statisticsInRangeResponseState.collectAsState()
    val servicesPopularityState by statisticsViewModel.servicesPopularityState.collectAsState()
    val snackBarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Date range state
    val (a, b) = DateTab.TODAY.getRange()
    var startDate by remember { mutableStateOf(a) }
    var endDate by remember { mutableStateOf(b) }

    LaunchedEffect(statisticsResponse) {
        when (statisticsResponse) {
            is StatisticsViewModel.StatisticsResponseState.Error -> {
                val errorMessage =
                    (statisticsResponse as StatisticsViewModel.StatisticsResponseState.Error).message
                snackBarHostState.showSnackbar(
                    message = errorMessage,
                    actionLabel = "OK"
                )
            }

            else -> {}
        }
    }

    LaunchedEffect(selectedTabIndex) {
        when (
            selectedTabIndex
        ) {
            0 -> statisticsViewModel.getStatistics()
            1 -> statisticsViewModel.getStatisticsInRange(startDate, endDate)
            2 -> statisticsViewModel.getServicesPopularity()
        }
    }


    // Update statistics when date range changes
    LaunchedEffect(startDate, endDate) {
        statisticsViewModel.getStatisticsInRange(startDate, endDate)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {

            // Tab Row
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Overall Statistics") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Monthly Statistics") }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    text = { Text("Services Popularity") }
                )
            }

            // Date range picker for monthly tab
            if (selectedTabIndex == 1) {
                DateRangeSelector(
                    onClick = { dateTab ->
                        val dateRange = dateTab.getRange()
                        startDate = dateRange.first
                        endDate = dateRange.second
                        statisticsViewModel.getStatisticsInRange(
                            startTime = startDate,
                            endTime = endDate
                        )
                    }
                )
                DateRangePickerDialog(onDateRangeSelected = { range ->
                    if (range == null) {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                visuals = object : SnackbarVisuals {
                                    override val actionLabel: String
                                        get() = "Đóng"
                                    override val duration: SnackbarDuration
                                        get() = SnackbarDuration.Short
                                    override val message: String
                                        get() = "Vui lòng chọn ngày hợp lệ"
                                    override val withDismissAction: Boolean
                                        get() = true
                                }
                            )
                        }
                        return@DateRangePickerDialog
                    }
                    startDate = range.first
                    endDate = range.second
                    statisticsViewModel.getStatisticsInRange(
                        startTime = startDate,
                        endTime = endDate
                    )
                })
            }

            // Tab Content
            when (selectedTabIndex) {
                0 -> {
                    when (statisticsResponse) {
                        is StatisticsViewModel.StatisticsResponseState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is StatisticsViewModel.StatisticsResponseState.Success -> {
                            val statistics =
                                (statisticsResponse as StatisticsViewModel.StatisticsResponseState.Success).data
                            StatisticsHomeContent(statistics)
                        }

                        else -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No data available")
                            }
                        }
                    }
                }

                1 -> {
                    when (statisticsInRangeResponse) {
                        is StatisticsViewModel.StatisticsInRangeResponseState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is StatisticsViewModel.StatisticsInRangeResponseState.Success -> {
                            val statistics =
                                (statisticsInRangeResponse as StatisticsViewModel.StatisticsInRangeResponseState.Success).data
                            StatisticsInRangeContent(statistics)
                        }

                        else -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No monthly data available")
                            }
                        }
                    }
                }

                2 -> {
                    when (servicesPopularityState) {
                        is StatisticsViewModel.ServicesPopularityState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is StatisticsViewModel.ServicesPopularityState.Success -> {
                            val stats =
                                (servicesPopularityState as StatisticsViewModel.ServicesPopularityState.Success).data
                            ServiceStatisticsContent(stats)
                        }

                        else -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No service statistics available")
                            }
                        }
                    }
                }
            }
        }
    }


}