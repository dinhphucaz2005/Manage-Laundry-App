package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val id: Int,
    val name: String,
    val email: String
)
