package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequest(
    val shopId: Int,
    val specialInstructions: String? = null,
    val items: List<Item>
) {
    @Serializable
    data class Item(
        val serviceId: Int,
        val quantity: Int
    )
}