package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ShopDetailResponse(
    val id: Int,
    val name: String,
    val location: String,
    val openTime: String,
    val closeTime: String,
    val services: List<ShopDetailServiceResponse> = emptyList(),
) {
    @Serializable
    data class ShopDetailServiceResponse(
        val id: Int,
        val name: String,
        val description: String,
        val price: Int,
    )
}
