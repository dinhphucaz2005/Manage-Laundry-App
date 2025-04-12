package com.example.manage.laundry.data.model.response

import com.example.manage.laundry.data.model.request.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val id: Int,
    val shopName: String,
    val customerName: String,
    val totalPrice: Double,
    val status: Order.Status,
    val specialInstructions: String?,
    val createdAt: String
)