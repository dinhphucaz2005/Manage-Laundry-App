package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderHistoryResponse(
    val orderId: Int,
    val shopName: String,
    val totalPrice: Double,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)
