package com.example.manage.laundry.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.manage.laundry.R

@SuppressLint("DefaultLocale")
fun formatCurrency(context: Context, amount: Double): String {
    val formattedNumber = String.format("%,d", amount.toInt()).replace(',', '.')
    return context.getString(R.string.vnd_format, formattedNumber)
}
