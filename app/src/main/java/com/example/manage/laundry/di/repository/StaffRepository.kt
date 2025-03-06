package com.example.manage.laundry.di.repository

import com.example.manage.laundry.data.model.request.StaffLoginRequest
import com.example.manage.laundry.data.model.request.UpdateOrderStatusRequest
import com.example.manage.laundry.data.model.response.ApiResponse
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.model.response.StaffLoginResponse

interface StaffRepository {
    suspend fun login(request: StaffLoginRequest): ApiResponse<StaffLoginResponse>

    suspend fun getOrders(): ApiResponse<List<OrderResponse>>

    suspend fun updateOrderStatus(
        orderId: Int,
        request: UpdateOrderStatusRequest
    ): ApiResponse<OrderResponse>
}