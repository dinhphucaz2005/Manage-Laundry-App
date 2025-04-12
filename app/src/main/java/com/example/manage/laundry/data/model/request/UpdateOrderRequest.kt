package com.example.manage.laundry.data.model.request

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
class Order {
    enum class Status {
        PENDING,
        CONFIRMED,
        PROCESSING,
        IN_PROGRESS,
        READY_FOR_DELIVERY,
        COMPLETED,
        CANCELLED;

        @Composable
        fun getColor() = when (this) {
            PENDING -> MaterialTheme.colorScheme.tertiary
            CONFIRMED -> MaterialTheme.colorScheme.primary
            PROCESSING -> MaterialTheme.colorScheme.secondary
            IN_PROGRESS -> MaterialTheme.colorScheme.inversePrimary
            READY_FOR_DELIVERY -> MaterialTheme.colorScheme.surfaceTint
            COMPLETED -> MaterialTheme.colorScheme.surfaceTint.copy(green = 0.8f)
            CANCELLED -> MaterialTheme.colorScheme.error
        }
    }


}

@Serializable
data class UpdateOrderRequest(
    val status: Order.Status,
    val specialInstructions: String? = null
)

