package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterOwnerResponse(
    val user: UserResponse,
    val shop: ShopResponse
)


