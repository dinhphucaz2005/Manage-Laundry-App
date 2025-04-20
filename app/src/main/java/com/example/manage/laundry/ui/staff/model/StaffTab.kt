package com.example.manage.laundry.ui.staff.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Payment
import androidx.compose.ui.graphics.vector.ImageVector

enum class StaffTab(
    val title: String,
    val icon: ImageVector,
) {
    ORDER("Orders", Icons.AutoMirrored.Filled.List),

    //    STATISTICS("Statistics", Icons.Default.BarChart),
    DELIVERY("Delivery", Icons.Default.DeliveryDining),
    PAYMENT("Payment", Icons.Default.Payment),
//    ACCOUNT("Account", Icons.Default.Settings)
}