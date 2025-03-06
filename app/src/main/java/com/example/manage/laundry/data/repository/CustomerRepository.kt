package com.example.manage.laundry.data.repository

import com.example.manage.laundry.data.model.request.CustomerLoginRequest
import com.example.manage.laundry.data.model.request.CustomerRegisterRequest
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.data.network.ApiService

class CustomerRepositoryImpl(
    private val apiService: ApiService
) : CustomerRepository{
    override suspend fun register(request: CustomerRegisterRequest): ApiResponse<RegisterCustomerResponse> =
        apiService.registerCustomer(request)

    override suspend fun login(request: CustomerLoginRequest): ApiResponse<CustomerLoginResponse> =
        apiService.loginCustomer(request)

    override suspend fun searchShops(): ApiResponse<List<ShopSearchResponse>> =
        apiService.searchShops()

    override suspend fun getOrderHistory(): ApiResponse<List<OrderHistoryResponse>> =
        apiService.getOrderHistory()

    override suspend fun trackOrder(orderId: Int): ApiResponse<TrackOrderResponse> =
        apiService.trackOrder(orderId)
}

interface CustomerRepository {
    suspend fun register(request: CustomerRegisterRequest): ApiResponse<RegisterCustomerResponse>

    suspend fun login(request: CustomerLoginRequest): ApiResponse<CustomerLoginResponse>

    suspend fun searchShops(): ApiResponse<List<ShopSearchResponse>>

    suspend fun getOrderHistory(): ApiResponse<List<OrderHistoryResponse>>

    suspend fun trackOrder(orderId: Int): ApiResponse<TrackOrderResponse>
}