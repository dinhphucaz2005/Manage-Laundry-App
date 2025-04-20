package com.example.manage.laundry.ui.owner.screen.statistic.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.response.ServicesStatistics
import java.text.NumberFormat

@Composable
fun ServiceStatCard(service: ServicesStatistics.PopularService, formatter: NumberFormat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = service.serviceName,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = "Total Usage",
                    value = service.totalUsage.toString(),
                    modifier = Modifier.weight(1f)
                )

                StatItem(
                    label = "Order Count",
                    value = service.orderCount.toString(),
                    modifier = Modifier.weight(1f)
                )

                StatItem(
                    label = "Revenue",
                    value = formatter.format(service.totalRevenue),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
