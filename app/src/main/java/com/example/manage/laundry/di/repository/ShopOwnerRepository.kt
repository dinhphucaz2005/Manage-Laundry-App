package com.example.manage.laundry.di.repository

import com.example.manage.laundry.data.model.request.CreateServiceRequest
import com.example.manage.laundry.data.model.request.OwnerLoginRequest
import com.example.manage.laundry.data.model.request.ShopRegisterRequest
import com.example.manage.laundry.data.model.request.StaffRegisterRequest
import com.example.manage.laundry.data.model.request.UpdateOrderRequest
import com.example.manage.laundry.data.model.request.UpdateServiceRequest
import com.example.manage.laundry.data.model.response.ApiResponse
import com.example.manage.laundry.data.model.response.LoginResponse
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.model.response.RegisterOwnerResponse
import com.example.manage.laundry.data.model.response.RegisterStaffResponse
import com.example.manage.laundry.data.model.response.ShopOrderResponse
import com.example.manage.laundry.data.model.response.ShopServiceResponse

interface ShopOwnerRepository {
    suspend fun register(request: ShopRegisterRequest): ApiResponse<RegisterOwnerResponse>

    suspend fun login(request: OwnerLoginRequest): ApiResponse<LoginResponse>

    suspend fun addStaff(
        shopId: Int,
        request: StaffRegisterRequest
    ): ApiResponse<RegisterStaffResponse>

    suspend fun addService(
        shopId: Int,
        request: CreateServiceRequest
    ): ApiResponse<ShopServiceResponse>

    suspend fun updateService(
        serviceId: Int,
        request: UpdateServiceRequest
    ): ApiResponse<ShopServiceResponse>

    suspend fun deleteService(serviceId: Int): ApiResponse<Unit>

    suspend fun getShopOrders(shopId: Int): ApiResponse<List<ShopOrderResponse>>

    suspend fun updateOrder(orderId: Int, request: UpdateOrderRequest): ApiResponse<OrderResponse>
}