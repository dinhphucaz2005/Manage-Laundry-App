package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
class Order {
    enum class Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CONFIRMED,
        PROCESSING
    }
}

@Serializable
data class UpdateOrderRequest(
    val status: Order.Status,
    val specialInstructions: String? = null
)

