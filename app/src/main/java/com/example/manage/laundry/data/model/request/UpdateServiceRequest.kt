package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateServiceRequest(
    val name: String,
    val description: String,
    val price: Int
)