package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ShopSearchResponse(
    val shopId: Int,
    val name: String,
    val location: String,
    val description: String,
    val openTime: String,
    val closeTime: String,
    val averageRating: Double
)
