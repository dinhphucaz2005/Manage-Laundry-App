package com.example.manage.laundry.ui.owner.screen.statistic

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.response.ServicesStatistics
import com.example.manage.laundry.data.model.response.StatisticsInRangeResponse
import com.example.manage.laundry.data.model.response.StatisticsResponse
import com.example.manage.laundry.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _statisticsResponse =
        MutableStateFlow<StatisticsResponseState>(StatisticsResponseState.Idle)
    val statisticsResponse: StateFlow<StatisticsResponseState> =
        _statisticsResponse.asStateFlow()

    private val _statisticsInRangeResponseState =
        MutableStateFlow<StatisticsInRangeResponseState>(StatisticsInRangeResponseState.Idle)
    val statisticsInRangeResponseState: StateFlow<StatisticsInRangeResponseState> =
        _statisticsInRangeResponseState.asStateFlow()

    private val _servicesPopularityState =
        MutableStateFlow<ServicesPopularityState>(ServicesPopularityState.Idle)
    val servicesPopularityState: StateFlow<ServicesPopularityState> =
        _servicesPopularityState.asStateFlow()


    fun getStatistics() {
        viewModelScope.launch {
            _statisticsResponse.value = StatisticsResponseState.Loading
            try {
                val response = apiService.getStatistics()
                if (response.success && response.data != null) {
                    _statisticsResponse.value = StatisticsResponseState.Success(response.data)
                } else {
                    _statisticsResponse.value = StatisticsResponseState.Error(response.message)
                }
            } catch (e: Exception) {
                _statisticsResponse.value =
                    StatisticsResponseState.Error(e.message ?: "Unknown error")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStatisticsInRange(
        startTime: LocalDateTime? = null,
        endTime: LocalDateTime? = null
    ) {
        viewModelScope.launch {
            _statisticsInRangeResponseState.value = StatisticsInRangeResponseState.Loading
            try {
                val response = apiService.getStatisticsInRange(
                    startTime = startTime ?: LocalDateTime.of(2024, 1, 1, 0, 0),
                    endTime = endTime ?: LocalDateTime.now()
                )
                if (response.success && response.data != null) {
                    _statisticsInRangeResponseState.value =
                        StatisticsInRangeResponseState.Success(response.data)
                } else {
                    _statisticsInRangeResponseState.value =
                        StatisticsInRangeResponseState.Error(response.message)
                }
            } catch (e: Exception) {
                _statisticsInRangeResponseState.value =
                    StatisticsInRangeResponseState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getServicesPopularity() {
        viewModelScope.launch {
            _servicesPopularityState.value = ServicesPopularityState.Loading
            try {
                val response = apiService.getServicesPopularity()
                if (response.success && response.data != null) {
                    _servicesPopularityState.value = ServicesPopularityState.Success(response.data)
                } else {
                    _servicesPopularityState.value = ServicesPopularityState.Error(response.message)
                }
            } catch (e: Exception) {
                _servicesPopularityState.value =
                    ServicesPopularityState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class StatisticsResponseState {
        data object Idle : StatisticsResponseState()
        data object Loading : StatisticsResponseState()
        data class Success(val data: StatisticsResponse) : StatisticsResponseState()
        data class Error(val message: String) : StatisticsResponseState()
    }

    sealed class StatisticsInRangeResponseState {
        data object Idle : StatisticsInRangeResponseState()
        data object Loading : StatisticsInRangeResponseState()
        data class Success(val data: StatisticsInRangeResponse) : StatisticsInRangeResponseState()
        data class Error(val message: String) : StatisticsInRangeResponseState()
    }

    sealed class ServicesPopularityState {
        data object Idle : ServicesPopularityState()
        data object Loading : ServicesPopularityState()
        data class Success(val data: ServicesStatistics) : ServicesPopularityState()
        data class Error(val message: String) : ServicesPopularityState()
    }
}