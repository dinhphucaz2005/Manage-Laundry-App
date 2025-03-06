package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderResponse(
    val orderId: Int,
    val totalPrice: Int,
    val status: String,
    val createdAt: String
)