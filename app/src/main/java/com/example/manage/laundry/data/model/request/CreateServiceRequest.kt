package com.example.manage.laundry.data.model.request

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CreateServiceRequest(
    val name: String,
    val description: String,
    val price: Int,
)