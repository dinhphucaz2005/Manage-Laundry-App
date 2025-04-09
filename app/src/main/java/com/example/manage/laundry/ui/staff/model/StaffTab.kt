package com.example.manage.laundry.ui.staff.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class StaffTab(
    val title: String,
    val icon: ImageVector,
) {
    ORDER("Orders", Icons.AutoMirrored.Filled.List),
    STATISTICS("Statistics", Icons.Default.BarChart),
    ACCOUNT("Account", Icons.Default.Settings)
}