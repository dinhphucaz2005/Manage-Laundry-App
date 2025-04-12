package com.example.manage.laundry.ui.customer.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class CustomerTab(
    val title: String,
    val icon: ImageVector,
) {
    HOME("Trang chủ", Icons.Default.Home),
    ORDER("Đơn hàng", Icons.Default.ShoppingCart),
    FAVORITE("Yêu thích", Icons.Default.Favorite),
    HISTORY("Lịch sử", Icons.Default.History),
    ACCOUNT("Tài khoản", Icons.Default.Person)
}
