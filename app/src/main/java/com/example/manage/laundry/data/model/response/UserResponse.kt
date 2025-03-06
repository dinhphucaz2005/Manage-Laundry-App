package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val role: String
)
