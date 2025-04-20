package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class StatisticsResponse(
    val totalOrders: Int,
    val ordersByStatus: Map<String, Int>,
    val totalRevenue: Long,
    val paymentMethods: Map<String, Int>,
    val popularServices: List<PopularService>
) {
    @Serializable
    data class PopularService(
        val serviceId: Int,
        val serviceName: String,
        val orderCount: Int,
        val totalQuantity: Int,
        val totalRevenue: Long
    )
}

