package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ShopServiceResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val shopId: Int
)
