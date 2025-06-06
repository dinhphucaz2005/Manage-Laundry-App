package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateOrderRequest(
    val status: Order.Status,
    val specialInstructions: String? = null
)

