package com.example.manage.laundry.data.model.response

import com.example.manage.laundry.data.model.request.Order
import kotlinx.serialization.Serializable

@Serializable
data class ShopOrderResponse(
    val orderId: Int,
    val customerName: String,
    val totalPrice: Int,
    val status: Order.Status,
    val createdAt: String
)
