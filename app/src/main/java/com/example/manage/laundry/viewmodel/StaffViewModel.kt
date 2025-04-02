package com.example.manage.laundry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.di.repository.StaffRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val repository: StaffRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _orderState = MutableStateFlow<OrderState>(OrderState.Idle)
    val orderState: StateFlow<OrderState> = _orderState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.login(StaffLoginRequest(email, password))
                if (response.success) {
                    _loginState.value = LoginState.Success(response.data)
                } else {
                    _loginState.value = LoginState.Error(response.message)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            _orderState.value = OrderState.Loading
            try {
                val response = repository.getOrders()
                if (response.success) {
                    _orderState.value = OrderState.Success(response.data ?: emptyList())
                } else {
                    _orderState.value = OrderState.Error(response.message)
                }
            } catch (e: Exception) {
                _orderState.value = OrderState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateOrderStatus(orderId: Int, status: String) {
        viewModelScope.launch {
            _orderState.value = OrderState.Loading
            try {
                val response =
                    repository.updateOrderStatus(orderId, UpdateOrderStatusRequest(status))
                if (response.success) {
                    _orderState.value = OrderState.Success(
                        _orderState.value.let { current ->
                            if (current is OrderState.Success) {
                                current.orders.map {
                                    if (it.id == orderId) response.data ?: it else it
                                }
                            } else emptyList()
                        }
                    )
                } else {
                    _orderState.value = OrderState.Error(response.message)
                }
            } catch (e: Exception) {
                _orderState.value = OrderState.Error(e.message ?: "Unknown error")
            }
        }
    }



// Sealed classes for different states

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val data: StaffLoginResponse?) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    sealed class OrderState {
        object Idle : OrderState()
        object Loading : OrderState()
        data class Success(val orders: List<OrderResponse>) : OrderState()
        data class Error(val message: String) : OrderState()
    }
}