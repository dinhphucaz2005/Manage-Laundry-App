package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ShopResponse(
    val id: Int,
    val name: String,
    val location: String,
    val openTime: String,
    val closeTime: String
)