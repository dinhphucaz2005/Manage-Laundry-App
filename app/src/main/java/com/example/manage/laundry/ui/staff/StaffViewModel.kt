package com.example.manage.laundry.ui.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.Order
import com.example.manage.laundry.data.model.request.StaffLoginRequest
import com.example.manage.laundry.data.model.request.UpdateOrderStatusRequest
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.model.response.StaffLoginResponse
import com.example.manage.laundry.di.repository.StaffRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val staffRepository: StaffRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _orderState = MutableStateFlow<OrderState>(OrderState.Idle)
    val orderState: StateFlow<OrderState> = _orderState.asStateFlow()

    private val _updateOrderState = MutableStateFlow<UpdateOrderState>(UpdateOrderState.Idle)
    val updateOrderState: StateFlow<UpdateOrderState> = _updateOrderState.asStateFlow()


    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = staffRepository.login(StaffLoginRequest(email, password))
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
                val response = staffRepository.getOrders()
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

    fun updateOrderStatus(orderId: Int, status: Order.Status) {
        viewModelScope.launch {
            _updateOrderState.value = UpdateOrderState.Loading
            try {
                val response = staffRepository
                    .updateOrderStatus(orderId, UpdateOrderStatusRequest(status.name))
                if (response.success) {
                    // Update the order in the current order state
                    val newOrderState = (_orderState.value as? OrderState.Success)
                        ?.orders?.mapNotNull {
                            if (it.id == orderId) {
                                if (status == Order.Status.CANCELLED || status == Order.Status.COMPLETED)
                                    null
                                else
                                    it.copy(status = status)
                            } else
                                it
                        } ?: emptyList()
                    _orderState.value = OrderState.Success(orders = newOrderState)
                    _updateOrderState.value = UpdateOrderState.Success
                } else {
                    _updateOrderState.value = UpdateOrderState.Error(response.message)
                }
            } catch (e: Exception) {
                _updateOrderState.value = UpdateOrderState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class LoginState {
        data object Idle : LoginState()
        data object Loading : LoginState()
        data class Success(val data: StaffLoginResponse?) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    sealed class OrderState {
        data object Idle : OrderState()
        data object Loading : OrderState()
        data class Success(val orders: List<OrderResponse>) : OrderState()
        data class Error(val message: String) : OrderState()
    }

    sealed class UpdateOrderState {
        data object Idle : UpdateOrderState()
        data object Loading : UpdateOrderState()
        data object Success : UpdateOrderState()
        data class Error(val message: String) : UpdateOrderState()
    }
}