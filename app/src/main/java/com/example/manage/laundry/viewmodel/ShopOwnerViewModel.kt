package com.example.manage.laundry.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.di.repository.ShopOwnerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopOwnerViewModel @Inject constructor(
    private val repository: ShopOwnerRepository
) : ViewModel() {
    var uiState by mutableStateOf(OwnerUiState())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.login(OwnerLoginRequest(email, password))
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

    fun register(
        ownerName: String,
        email: String,
        password: String,
        phone: String,
        shopName: String,
        address: String,
        openTime: String,
        closeTime: String
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.register(
                    ShopRegisterRequest(
                        ownerName,
                        email,
                        password,
                        phone,
                        shopName,
                        address,
                        openTime,
                        closeTime
                    )
                )
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

    fun addStaff(shopId: Int, name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val response =
                    repository.addStaff(shopId, StaffRegisterRequest(name, email, password, phone))
                uiState = if (response.success) {
                    uiState.copy(
                        staffList = uiState.staffList + (response.data?.staff ?: return@launch),
                        isLoading = false
                    )
                } else {
                    uiState.copy(error = response.message, isLoading = false)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun addService(shopId: Int, name: String, description: String, price: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response =
                    repository.addService(shopId, CreateServiceRequest(name, description, price))
                if (response.success) {
                    uiState.copy(
                        services = uiState.services + (response.data ?: return@launch),
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

    fun updateService(serviceId: Int, name: String, description: String, price: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.updateService(
                    serviceId,
                    UpdateServiceRequest(name, description, price)
                )
                if (response.success) {
                    uiState.copy(
                        services = uiState.services.map {
                            if (it.id == serviceId) response.data ?: it else it
                        },
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

    fun deleteService(serviceId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.deleteService(serviceId)
                if (response.success) {
                    uiState.copy(
                        services = uiState.services.filter { it.id != serviceId },
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

    fun getShopOrders(shopId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response = repository.getShopOrders(shopId)
                if (response.success) {
                    uiState.copy(
                        shopOrders = response.data ?: emptyList(),
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

    fun updateOrder(orderId: Int, status: Order.Status, specialInstructions: String?) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = try {
                val response =
                    repository.updateOrder(orderId, UpdateOrderRequest(status, specialInstructions))
                if (response.success) {
                    uiState.copy(
                        shopOrders = uiState.shopOrders.map {
                            if (it.orderId == orderId) response.data?.let {it1 ->
                                ShopOrderResponse(
                                    it1.id,
                                    it1.customerName,
                                    it1.totalPrice,
                                    it1.status,
                                    it1.createdAt
                                )
                            }
                                ?: it else it
                        },
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

data class OwnerUiState(
    val loginResponse: LoginResponse? = null,
    val registerResponse: RegisterOwnerResponse? = null,
    val staffList: List<UserResponse> = emptyList(),
    val services: List<ShopServiceResponse> = emptyList(),
    val shopOrders: List<ShopOrderResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)