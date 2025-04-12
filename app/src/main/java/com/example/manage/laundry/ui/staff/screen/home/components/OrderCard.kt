package com.example.manage.laundry.ui.staff.screen.home.components

import android.annotation.SuppressLint
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.utils.formatCurrency


@SuppressLint("DefaultLocale")
@Composable
fun OrderCard(
    order: OrderResponse,
    onUpdateClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        colors = CardDefaults.cardColors(
            containerColor = order.status.getColor().copy(alpha = 0.5f),
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order #${order.id}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                StatusIndicator(status = order.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Khách hàng: ${order.customerName}")
            Text(
                "Tổng tiền: ${
                    formatCurrency(
                        context = context,
                        amount = order.totalPrice,
                    )
                }"
            )
            Text("Date: ${order.getCreateAtString()}")

            if (!order.specialInstructions.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Special Instructions:",
                    fontWeight = FontWeight.Bold
                )
                Text(text = order.specialInstructions)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onUpdateClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("UPDATE STATUS")
            }
        }
    }
}
