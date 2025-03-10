package com.example.manage.laundry.data.repository

import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.di.repository.StaffRepository

class StaffRepositoryImpl(
    private val apiService: ApiService
) : StaffRepository {
    override suspend fun login(request: StaffLoginRequest): ApiResponse<StaffLoginResponse> =
        apiService.loginStaff(request)

    override suspend fun getOrders(): ApiResponse<List<OrderResponse>> =
        apiService.getStaffOrders()

    override suspend fun updateOrderStatus(
        orderId: Int,
        request: UpdateOrderStatusRequest
    ): ApiResponse<OrderResponse> =
        apiService.updateOrderStatus(orderId, request)
}

