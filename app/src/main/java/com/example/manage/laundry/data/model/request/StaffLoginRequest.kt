package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class StaffLoginRequest(
    val email: String,
    val password: String
)