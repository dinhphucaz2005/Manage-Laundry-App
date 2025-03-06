package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class OwnerLoginRequest(
    val email: String,
    val password: String
)