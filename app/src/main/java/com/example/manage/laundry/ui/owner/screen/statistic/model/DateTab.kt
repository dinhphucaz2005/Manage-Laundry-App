package com.example.manage.laundry.ui.owner.screen.statistic.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

enum class DateTab(val label: String) {
    TODAY("Today"),
    LAST_7_DAYS("Last 7 Days"),
    THIS_MONTH("This Month"),
    LAST_MONTH("Last Month");

    @RequiresApi(Build.VERSION_CODES.O)
    fun getRange(): Pair<LocalDateTime, LocalDateTime> {
        val today = LocalDateTime.now()

        return when (this) {
            TODAY -> Pair(
                LocalDateTime.of(today.year, today.month, today.dayOfMonth, 0, 0),
                today
            )

            LAST_7_DAYS -> {
                val weekAgo = today.minusDays(7)
                Pair(weekAgo, today)
            }

            THIS_MONTH -> {
                val firstOfMonth = LocalDateTime.of(today.year, today.month, 1, 0, 0)
                Pair(firstOfMonth, today)
            }

            LAST_MONTH -> {
                val firstOfLastMonth = LocalDateTime.of(
                    today.minusMonths(1).year,
                    today.minusMonths(1).month,
                    1,
                    0,
                    0
                )
                val lastOfLastMonth = LocalDateTime.of(
                    today.year,
                    today.month,
                    1,
                    0,
                    0
                ).minusDays(1)
                Pair(firstOfLastMonth, lastOfLastMonth)
            }
        }
    }
}
