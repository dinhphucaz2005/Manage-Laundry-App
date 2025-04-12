package com.example.manage.laundry.data.repository

import com.example.manage.laundry.data.model.request.StaffLoginRequest
import com.example.manage.laundry.data.model.request.UpdateOrderStatusRequest
import com.example.manage.laundry.data.model.response.ApiResponse
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.model.response.StaffLoginResponse
import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.di.repository.StaffRepository

class StaffRepositoryImpl(
    private val apiService: ApiService
) : StaffRepository {
    override suspend fun login(request: StaffLoginRequest): ApiResponse<StaffLoginResponse> {
        val result = apiService.loginStaff(request)
        if (result.success && result.data != null) {
            ApiService.token = result.data.token
        }
        return result
    }

    override suspend fun getOrders(): ApiResponse<List<OrderResponse>> =
        apiService.getStaffOrders()

    override suspend fun updateOrderStatus(
        orderId: Int,
        request: UpdateOrderStatusRequest
    ): ApiResponse<Nothing> =
        apiService.updateOrderStatus(orderId, request)
}

