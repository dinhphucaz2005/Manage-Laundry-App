package com.example.manage.laundry.service.dto

data class OrderNotification(
    var message: String? = null,
    var orderId: Long? = null,
    var status: String? = null,
    var timestamp: Long? = null
)

