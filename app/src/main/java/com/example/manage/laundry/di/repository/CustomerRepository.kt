package com.example.manage.laundry.di.repository

import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*

interface CustomerRepository {
    suspend fun register(request: CustomerRegisterRequest): ApiResponse<RegisterCustomerResponse>

    suspend fun login(request: CustomerLoginRequest): ApiResponse<CustomerLoginResponse>

    suspend fun searchShops(): ApiResponse<List<ShopSearchResponse>>

    suspend fun getOrderHistory(): ApiResponse<List<OrderResponse>>

    suspend fun trackOrder(orderId: Int): ApiResponse<OrderResponse>

    suspend fun createOrder(request: CreateOrderRequest): ApiResponse<CreateOrderResponse>

    suspend fun getShopDetails(shopId: Int): ApiResponse<ShopDetailResponse>
}