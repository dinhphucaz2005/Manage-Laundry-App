package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmOrderRequest(
    val newPrice: Int?,
    val staffResponse: String?,
)