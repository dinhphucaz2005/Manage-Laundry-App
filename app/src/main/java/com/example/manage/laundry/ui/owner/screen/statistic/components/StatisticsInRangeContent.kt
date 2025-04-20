package com.example.manage.laundry.ui.owner.screen.statistic.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.response.StatisticsInRangeResponse
import com.example.manage.laundry.utils.formatCurrency


@Composable
fun StatisticsInRangeContent(statistics: StatisticsInRangeResponse) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Overview Statistics",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            StatsCard(
                title = "Total Orders",
                value = statistics.totalOrders.toString(),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
        }

        item {
            StatsCard(
                title = "Total Revenue",
                value = formatCurrency(statistics.totalRevenue),
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }

        item {
            StatsCard(
                title = "Average Order Value",
                value = formatCurrency(statistics.averageOrderValue),
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        }

        item {
            Text(
                text = "Daily Revenue",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        val sortedEntries = statistics.revenueByDay.entries.sortedByDescending { it.key }

        items(sortedEntries) { (date, revenue) ->
            DailyRevenueItem(
                date = date,
                revenue = formatCurrency(revenue)
            )
        }
    }

}
