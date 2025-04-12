package com.example.manage.laundry.data.model.response

import com.example.manage.laundry.data.model.request.Order
import kotlinx.serialization.Serializable

@Serializable
data class TrackOrderResponse(
    val orderId: Int,
    val shopName: String,
    val status: Order.Status,
    val totalPrice: Double,
    val specialInstructions: String?,
    val createdAt: String,
    val updatedAt: String
)