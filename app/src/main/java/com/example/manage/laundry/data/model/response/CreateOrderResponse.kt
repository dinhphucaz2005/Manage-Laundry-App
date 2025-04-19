package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderResponse(
    val orderId: Int,
    val estimatePrice: Int,
    val status: String,
    val createdAt: String
)