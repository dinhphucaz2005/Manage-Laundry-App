package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse<T>(
    val success: Boolean = true,
    val message: String = "",
    val data: T? = null
)

