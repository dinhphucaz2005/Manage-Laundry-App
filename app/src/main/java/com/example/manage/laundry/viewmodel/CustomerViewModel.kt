package com.example.manage.laundry.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.di.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    var uiState by mutableStateOf(CustomerUiState())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.login(CustomerLoginRequest(email, password))
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

    fun register(name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.register(CustomerRegisterRequest(name, email, password, phone))
                if (response.success) {
                    uiState.copy(
                        registerResponse = response.data,
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

    fun searchShops() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.searchShops()
                if (response.success) {
                    uiState.copy(
                        shopList = response.data ?: emptyList(),
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

    fun getOrderHistory() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.getOrderHistory()
                if (response.success) {
                    uiState.copy(
                        orderHistory = response.data ?: emptyList(),
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

    fun trackOrder(orderId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.trackOrder(orderId)
                if (response.success) {
                    uiState.copy(
                        trackedOrder = response.data,
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

data class CustomerUiState(
    val loginResponse: CustomerLoginResponse? = null,
    val registerResponse: RegisterCustomerResponse? = null,
    val shopList: List<ShopSearchResponse> = emptyList(),
    val orderHistory: List<OrderHistoryResponse> = emptyList(),
    val trackedOrder: TrackOrderResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)