package com.example.manage.laundry.data.model.response

import android.annotation.SuppressLint
import com.example.manage.laundry.data.model.request.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val id: Int,
    val shopName: String = "",
    val customerName: String? = null,
    private val estimatePrice: Int,
    private val totalPrice: Int? = null,
    val status: Order.Status = Order.Status.PENDING,
    val specialInstructions: String? = null,
    val staffResponse: String? = null,
    private val createdAt: String? = null,
    private val updatedAt: String? = null,
    val items: List<OrderItemResponse> = emptyList()
) {

    val estimatePriceInt: Int
        get() = estimatePrice

    val estimatePriceString: String
        @SuppressLint("DefaultLocale")
        get() = String.format("%,.0fđ", estimatePrice.toFloat())

    val totalPriceString: String
        @SuppressLint("DefaultLocale")
        get() = totalPrice?.let { String.format("%,.0fđ", it.toFloat()) } ?: "Không có thông tin"


    val createdAtString: String
        get() = createdAt?.let {
            val date = it.split("T").first()
            val time = it.split("T").last().split(".").first()
            "$date $time"
        } ?: "Không có thông tin"

    val updatedAtString: String
        get() = updatedAt?.let {
            val date = it.split("T").first()
            val time = it.split("T").last().split(".").first()
            "$date $time"
        } ?: "Không có thông tin"

    @Serializable
    data class OrderItemResponse(
        val id: Int,
        val name: String,
        val quantity: Int,
        val price: Int,
        val totalPrice: Int
    )
}
