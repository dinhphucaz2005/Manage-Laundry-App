package com.example.manage.laundry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.di.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<CustomerState.Login>(CustomerState.Login.Idle)
    val loginState: StateFlow<CustomerState.Login> = _loginState

    private val _registerState =
        MutableStateFlow<CustomerState.Register>(CustomerState.Register.Idle)
    val registerState: StateFlow<CustomerState.Register> = _registerState

    private val _shopSearchState =
        MutableStateFlow<CustomerState.ShopSearch>(CustomerState.ShopSearch.Idle)
    val shopSearchState: StateFlow<CustomerState.ShopSearch> = _shopSearchState


    private val _createOrderState =
        MutableStateFlow<CustomerState.CreateOrder>(CustomerState.CreateOrder.Idle)
    val createOrderState: StateFlow<CustomerState.CreateOrder> = _createOrderState

    private val _orderHistoryState =
        MutableStateFlow<CustomerState.OrderHistory>(CustomerState.OrderHistory.Idle)
    val orderHistoryState: StateFlow<CustomerState.OrderHistory> = _orderHistoryState

    private val _trackOrderState =
        MutableStateFlow<CustomerState.TrackOrder>(CustomerState.TrackOrder.Idle)
    val trackOrderState: StateFlow<CustomerState.TrackOrder> = _trackOrderState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = CustomerState.Login.Loading
            try {
                val response = repository.login(CustomerLoginRequest(email, password))
                _loginState.value = if (response.success && response.data != null) {
                    CustomerState.Login.Success(response.data)
                } else {
                    CustomerState.Login.Error(response.message)
                }
            } catch (e: Exception) {
                _loginState.value = CustomerState.Login.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun register(name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            _registerState.value = CustomerState.Register.Loading
            try {
                val response =
                    repository.register(CustomerRegisterRequest(name, email, password, phone))
                _registerState.value = if (response.success && response.data != null) {
                    CustomerState.Register.Success(response.data)
                } else {
                    CustomerState.Register.Error(response.message)
                }
            } catch (e: Exception) {
                _registerState.value =
                    CustomerState.Register.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun createOrder(request: CreateOrderRequest) {
        viewModelScope.launch {
        }
    }

    fun searchShops() {
        viewModelScope.launch {
            _shopSearchState.value = CustomerState.ShopSearch.Loading
            try {
                val response = repository.searchShops()
                _shopSearchState.value = if (response.success) {
                    CustomerState.ShopSearch.Success(response.data ?: emptyList())
                } else {
                    CustomerState.ShopSearch.Error(response.message)
                }
            } catch (e: Exception) {
                _shopSearchState.value =
                    CustomerState.ShopSearch.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun getOrderHistory() {
        viewModelScope.launch {
            _orderHistoryState.value = CustomerState.OrderHistory.Loading
            try {
                val response = repository.getOrderHistory()
                _orderHistoryState.value = if (response.success) {
                    CustomerState.OrderHistory.Success(response.data ?: emptyList())
                } else {
                    CustomerState.OrderHistory.Error(response.message)
                }
            } catch (e: Exception) {
                _orderHistoryState.value =
                    CustomerState.OrderHistory.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun trackOrder(orderId: Int) {
        viewModelScope.launch {
            _trackOrderState.value = CustomerState.TrackOrder.Loading
            try {
                val response = repository.trackOrder(orderId)
                _trackOrderState.value = if (response.success && response.data != null) {
                    CustomerState.TrackOrder.Success(response.data)
                } else {
                    CustomerState.TrackOrder.Error(response.message)
                }
            } catch (e: Exception) {
                _trackOrderState.value =
                    CustomerState.TrackOrder.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}

sealed class CustomerState {
    data object Idle : CustomerState()

    sealed class Login : CustomerState() {
        data object Idle : Login()
        data object Loading : Login()
        data class Success(val response: CustomerLoginResponse) : Login()
        data class Error(val message: String) : Login()
    }

    sealed class Register : CustomerState() {
        data object Idle : Register()
        data object Loading : Register()
        data class Success(val response: RegisterCustomerResponse) : Register()
        data class Error(val message: String) : Register()
    }

    sealed class ShopSearch : CustomerState() {
        data object Idle : ShopSearch()
        data object Loading : ShopSearch()
        data class Success(val shops: List<ShopSearchResponse>) : ShopSearch()
        data class Error(val message: String) : ShopSearch()
    }

    sealed class CreateOrder : CustomerState() {
        data object Idle : CreateOrder()
        data object Loading : CreateOrder()
        data class Success(val orders: List<CreateOrderResponse>) : CreateOrder()
        data class Error(val message: String) : CreateOrder()
    }

    sealed class OrderHistory : CustomerState() {
        data object Idle : OrderHistory()
        data object Loading : OrderHistory()
        data class Success(val orders: List<OrderHistoryResponse>) : OrderHistory()
        data class Error(val message: String) : OrderHistory()
    }

    sealed class TrackOrder : CustomerState() {
        data object Idle : TrackOrder()
        data object Loading : TrackOrder()
        data class Success(val order: TrackOrderResponse) : TrackOrder()
        data class Error(val message: String) : TrackOrder()
    }
}
