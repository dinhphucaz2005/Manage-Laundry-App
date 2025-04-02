package com.example.manage.laundry.ui.staff.model

enum class StaffTab(
    val title: String,
    val icon: ImageVe,
) {
    ORDER("Orders", Icons.Default.List),
    STATISTICS("Statistics", Icons.Default.BarChart),
    ACCOUNT("Account", Icons.Default.Settings)
}