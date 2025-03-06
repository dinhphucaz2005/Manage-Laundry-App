package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterStaffResponse(
    val staff: UserResponse,
    val shop: ShopResponse
)

