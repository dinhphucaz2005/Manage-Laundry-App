package com.example.manage.laundry.network

@kotlinx.serialization.Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)
