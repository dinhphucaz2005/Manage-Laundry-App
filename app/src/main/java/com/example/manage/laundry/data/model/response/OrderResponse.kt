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
) {
    fun getCreateAtString(): String {
        val regex = Regex("""(\d{4})-(\d{2})-(\d{2})T(\d{2}:\d{2}:\d{2})(?:\.\d+)?""")
        return regex.replace(createdAt, "$4 $3-$2-$1")
    }
}