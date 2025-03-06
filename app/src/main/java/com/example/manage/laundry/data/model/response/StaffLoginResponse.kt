package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class StaffLoginResponse(
    val token: String,
    val staffId: Int,
    val shopId: Int,
    val name: String
)
