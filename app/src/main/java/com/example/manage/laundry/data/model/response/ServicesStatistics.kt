package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ServicesStatistics(
    val serviceStats: List<PopularService>,
) {
    @Serializable
    data class PopularService(
        val serviceId: Int,
        val serviceName: String,
        val totalUsage: Long,
        val totalRevenue: Long,
        val orderCount: Long
    )
}