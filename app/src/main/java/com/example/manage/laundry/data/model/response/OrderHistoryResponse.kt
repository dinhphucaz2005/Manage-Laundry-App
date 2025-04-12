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
) {
    fun getCreateAtString(): String {
        val regex = Regex("""(\d{4})-(\d{2})-(\d{2})T(\d{2}:\d{2}:\d{2})(?:\.\d+)?""")
        return regex.replace(createdAt, "$4 $3-$2-$1")
    }
}
