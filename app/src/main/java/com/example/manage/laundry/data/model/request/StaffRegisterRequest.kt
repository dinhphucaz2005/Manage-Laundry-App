package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class StaffRegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)