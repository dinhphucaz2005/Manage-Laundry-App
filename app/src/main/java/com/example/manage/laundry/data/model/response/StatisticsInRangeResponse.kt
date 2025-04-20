package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class StatisticsInRangeResponse(
    val totalOrders: Int,
    val totalRevenue: Long,
    val averageOrderValue: Long,
    val revenueByDay: Map<String, Long>
)