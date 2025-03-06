package com.example.manage.laundry.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.data.repository.StaffRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val repository: StaffRepository
) : ViewModel() {
    var uiState by mutableStateOf(StaffUiState())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.login(StaffLoginRequest(email, password))
                if (response.success) {
                    uiState.copy(
                        loginResponse = response.data,
                        isLoading = false
                    )
                } else {
                    uiState.copy(error = response.message, isLoading = false)
                }
            } catch (e: Exception) {
                uiState.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.getOrders()
                if (response.success) {
                    uiState.copy(
                        orders = response.data ?: emptyList(),
                        isLoading = false
                    )
                } else {
                    uiState.copy(error = response.message, isLoading = false)
                }
            } catch (e: Exception) {
                uiState.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun updateOrderStatus(orderId: Int, status: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.updateOrderStatus(orderId, UpdateOrderStatusRequest(status))
                if (response.success) {
                    uiState.copy(
                        orders = uiState.orders.map { if (it.id == orderId) response.data ?: it else it },
                        isLoading = false
                    )
                } else {
                    uiState.copy(error = response.message, isLoading = false)
                }
            } catch (e: Exception) {
                uiState.copy(error = e.message, isLoading = false)
            }
        }
    }
}

data class StaffUiState(
    val loginResponse: StaffLoginResponse? = null,
    val orders: List<OrderResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)