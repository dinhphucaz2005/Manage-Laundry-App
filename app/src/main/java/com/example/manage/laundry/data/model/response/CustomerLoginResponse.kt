package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CustomerLoginResponse(
    val token: String,
    val name: String,
    val email: String,
    val phone: String
)
