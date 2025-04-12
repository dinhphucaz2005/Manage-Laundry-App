package com.example.manage.laundry.data.repository

import com.example.manage.laundry.data.model.request.CreateOrderRequest
import com.example.manage.laundry.data.model.request.CustomerLoginRequest
import com.example.manage.laundry.data.model.request.CustomerRegisterRequest
import com.example.manage.laundry.data.model.response.ApiResponse
import com.example.manage.laundry.data.model.response.CreateOrderResponse
import com.example.manage.laundry.data.model.response.CustomerLoginResponse
import com.example.manage.laundry.data.model.response.OrderHistoryResponse
import com.example.manage.laundry.data.model.response.RegisterCustomerResponse
import com.example.manage.laundry.data.model.response.ShopDetailResponse
import com.example.manage.laundry.data.model.response.ShopSearchResponse
import com.example.manage.laundry.data.model.response.TrackOrderResponse
import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.di.repository.CustomerRepository

class CustomerRepositoryImpl(
    private val apiService: ApiService
) : CustomerRepository {
    override suspend fun register(request: CustomerRegisterRequest): ApiResponse<RegisterCustomerResponse> =
        apiService.registerCustomer(request)

    override suspend fun login(request: CustomerLoginRequest): ApiResponse<CustomerLoginResponse> {
        val result = apiService.loginCustomer(request)
        if (result.success && result.data != null) {
            ApiService.token = result.data.token
        }
        return result
    }

    override suspend fun searchShops(): ApiResponse<List<ShopSearchResponse>> =
        apiService.searchShops()

    override suspend fun getOrderHistory(): ApiResponse<List<OrderHistoryResponse>> =
        apiService.getOrderHistory()

    override suspend fun trackOrder(orderId: Int): ApiResponse<TrackOrderResponse> =
        apiService.trackOrder(orderId)

    override suspend fun createOrder(request: CreateOrderRequest): ApiResponse<CreateOrderResponse> =
        apiService.createOrder(request)

    override suspend fun getShopDetails(shopId: Int): ApiResponse<ShopDetailResponse> =
        apiService.getShopDetails(shopId)
}

