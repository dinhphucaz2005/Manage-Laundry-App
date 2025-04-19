package com.example.manage.laundry.ui.customer

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.CreateOrderRequest
import com.example.manage.laundry.data.model.request.CustomerLoginRequest
import com.example.manage.laundry.data.model.request.CustomerRegisterRequest
import com.example.manage.laundry.data.model.response.CreateOrderResponse
import com.example.manage.laundry.data.model.response.CustomerLoginResponse
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.model.response.RegisterCustomerResponse
import com.example.manage.laundry.data.model.response.ShopDetailResponse
import com.example.manage.laundry.data.model.response.ShopSearchResponse
import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.di.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: CustomerRepository,
    private val apiService: ApiService,
) : ViewModel() {

    private val _loginState = MutableStateFlow<CustomerState.Login>(CustomerState.Login.Idle)
    val loginState: StateFlow<CustomerState.Login> = _loginState

    private val _registerState =
        MutableStateFlow<CustomerState.Register>(CustomerState.Register.Idle)
    val registerState: StateFlow<CustomerState.Register> = _registerState

    private val _shopSearchState =
        MutableStateFlow<CustomerState.ShopSearch>(CustomerState.ShopSearch.Idle)
    val shopSearchState: StateFlow<CustomerState.ShopSearch> = _shopSearchState

    private val _shopDetailState =
        MutableStateFlow<CustomerState.ShopDetail>(CustomerState.ShopDetail.Idle)
    val shopDetailState: StateFlow<CustomerState.ShopDetail> = _shopDetailState


    private val _createOrderState =
        MutableStateFlow<CustomerState.CreateOrder>(CustomerState.CreateOrder.Idle)
    val createOrderState: StateFlow<CustomerState.CreateOrder> = _createOrderState

    private val _orderHistoryState =
        MutableStateFlow<CustomerState.OrderHistory>(CustomerState.OrderHistory.Idle)
    val orderHistoryState: StateFlow<CustomerState.OrderHistory> = _orderHistoryState

    private val _confirmOrderState =
        MutableStateFlow<CustomerState.ConfirmOrder>(CustomerState.ConfirmOrder.Idle)
    val confirmOrderState: StateFlow<CustomerState.ConfirmOrder> = _confirmOrderState

    private val _cancelOrderState =
        MutableStateFlow<CustomerState.CancelOrder>(CustomerState.CancelOrder.Idle)
    val cancelOrderState: StateFlow<CustomerState.CancelOrder> = _cancelOrderState

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


    fun createOrder() {
        viewModelScope.launch {
            _createOrderState.value = CustomerState.CreateOrder.Loading
            try {
                val orderRequests = _orderRequests.value
                if (orderRequests.isEmpty()) {
                    _createOrderState.value =
                        CustomerState.CreateOrder.Error("Giỏ hàng trống")
                    return@launch
                }
                val orderRequest = orderRequests[0].copy(
                    items = orderRequests.map {
                        CreateOrderRequest.Item(
                            serviceId = it.items[0].serviceId,
                            quantity = it.items[0].quantity
                        )
                    }
                )
                val response = repository.createOrder(orderRequest)
                _createOrderState.value = if (response.success && response.data != null) {
                    CustomerState.CreateOrder.Success(response.data)
                } else {
                    CustomerState.CreateOrder.Error(response.message)
                }
            } catch (e: Exception) {
                _createOrderState.value =
                    CustomerState.CreateOrder.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    private val _orderRequests =
        MutableStateFlow<MutableList<CreateOrderRequest>>(mutableStateListOf())

    fun addToCart(orderRequest: CreateOrderRequest): String {
        _createOrderState.value = CustomerState.CreateOrder.Idle
        // Check if cart contains items from another shop
        if (_orderRequests.value.isNotEmpty() && _orderRequests.value.any { it.shopId != orderRequest.shopId }) {
            return "Giỏ hàng chỉ chứa sản phẩm từ một cửa hàng"
        }

        // Add item to cart
        val currentCart = _orderRequests.value.toMutableList()
        currentCart.add(orderRequest)
        _orderRequests.value = currentCart

        return "Thêm vào giỏ hàng thành công"
    }

    data class CartItem(
        val serviceId: Int,
        val serviceName: String,
        val price: Int,
        val quantity: Int,
        val specialInstructions: String? = null
    )

    fun getCartItems(): List<CartItem> {
        val result = mutableListOf<CartItem>()
        val shopDetailState = _shopDetailState.value

        if (shopDetailState is CustomerState.ShopDetail.Success) {
            val shop = shopDetailState.shop

            _orderRequests.value.forEach { orderRequest ->
                orderRequest.items.forEach { item ->
                    val service = shop.services.find { it.id == item.serviceId }
                    service?.let {
                        result.add(
                            CartItem(
                                serviceId = it.id,
                                serviceName = it.name,
                                price = it.price,
                                quantity = item.quantity,
                                specialInstructions = orderRequest.specialInstructions
                            )
                        )
                    }
                }
            }
        }

        return result
    }

    fun getCartItemCount(): Int {
        return _orderRequests.value.sumOf { order -> order.items.sumOf { it.quantity } }
    }

    fun getCartTotal(): Int {
        return getCartItems().sumOf { it.price * it.quantity }
    }

    fun clearCart() {
        _orderRequests.value = mutableStateListOf()
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

    fun getShopDetails(shopId: Int) {
        viewModelScope.launch {
            _shopDetailState.value = CustomerState.ShopDetail.Loading
            try {
                val response = repository.getShopDetails(shopId)
                _shopDetailState.value = if (response.success && response.data != null) {
                    CustomerState.ShopDetail.Success(response.data)
                } else {
                    CustomerState.ShopDetail.Error(response.message)
                }
            } catch (e: Exception) {
                _shopDetailState.value =
                    CustomerState.ShopDetail.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun confirmOrder(orderId: Int) {
        viewModelScope.launch {
            _confirmOrderState.value = CustomerState.ConfirmOrder.Loading
            try {

                apiService.customerConfirmOrder(
                    orderId = orderId,
                ).let { response ->
                    if (response.success)
                        _confirmOrderState.value =
                            CustomerState.ConfirmOrder.Success(response.message)
                    else
                        _confirmOrderState.value =
                            CustomerState.ConfirmOrder.Error(response.message)
                }
            } catch (e: Exception) {
                _confirmOrderState.value =
                    CustomerState.ConfirmOrder.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun cancelOrder(orderId: Int) {
        viewModelScope.launch {
            _cancelOrderState.value = CustomerState.CancelOrder.Loading
            try {
                apiService.customerCancelOrder(
                    orderId = orderId,
                ).let { response ->
                    if (response.success)
                        _cancelOrderState.value =
                            CustomerState.CancelOrder.Success(response.message)
                    else
                        _cancelOrderState.value =
                            CustomerState.CancelOrder.Error(response.message)
                }
            } catch (e: Exception) {
                _cancelOrderState.value =
                    CustomerState.CancelOrder.Error(e.message ?: "Lỗi không xác định")
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

    sealed class ShopDetail : CustomerState() {
        data object Idle : ShopDetail()
        data object Loading : ShopDetail()
        data class Success(val shop: ShopDetailResponse) : ShopDetail()
        data class Error(val message: String) : ShopDetail()
    }

    sealed class CreateOrder : CustomerState() {
        data object Idle : CreateOrder()
        data object Loading : CreateOrder()
        data class Success(val orders: CreateOrderResponse?) : CreateOrder()
        data class Error(val message: String) : CreateOrder()
    }

    sealed class OrderHistory : CustomerState() {
        data object Idle : OrderHistory()
        data object Loading : OrderHistory()
        data class Success(val orders: List<OrderResponse>) : OrderHistory()
        data class Error(val message: String) : OrderHistory()
    }

    sealed class TrackOrder : CustomerState() {
        data object Idle : TrackOrder()
        data object Loading : TrackOrder()
        data class Success(val order: OrderResponse) : TrackOrder()
        data class Error(val message: String) : TrackOrder()
    }

    sealed class ConfirmOrder : CustomerState() {
        data object Idle : ConfirmOrder()
        data object Loading : ConfirmOrder()
        data class Success(val message: String) : ConfirmOrder()
        data class Error(val message: String) : ConfirmOrder()
    }

    sealed class CancelOrder : CustomerState() {
        data object Idle : CancelOrder()
        data object Loading : CancelOrder()
        data class Success(val message: String) : CancelOrder()
        data class Error(val message: String) : CancelOrder()
    }

}
