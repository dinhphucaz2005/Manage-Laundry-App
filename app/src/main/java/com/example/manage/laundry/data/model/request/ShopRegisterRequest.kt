package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ShopRegisterRequest(
    val ownerName: String,
    val email: String,
    val password: String,
    val phone: String,
    val shopName: String,
    val address: String,
    val openTime: String,
    val closeTime: String
)