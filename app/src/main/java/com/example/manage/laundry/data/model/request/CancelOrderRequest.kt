package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CancelOrderRequest(
    val reason: String,
)