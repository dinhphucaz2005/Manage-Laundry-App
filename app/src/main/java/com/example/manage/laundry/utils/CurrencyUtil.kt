package com.example.manage.laundry.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.manage.laundry.R

@SuppressLint("DefaultLocale")
@Composable
fun formatCurrency(amount: Any): String {
    val value = when (amount) {
        is String -> amount.toLongOrNull() ?: 0L
        is Int -> amount.toLong()
        is Long -> amount
        else -> return "Không có thông tin"
    }
    val context = LocalContext.current
    val formattedNumber = String.format("%,d", value).replace(',', '.')
    return context.getString(R.string.vnd_format, formattedNumber)
}
