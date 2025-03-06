package com.example.manage.laundry.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterCustomerResponse(
    val customerId: Int,
    val userId: Int,
    val name: String,
    val email: String,
    val phone: String
)
