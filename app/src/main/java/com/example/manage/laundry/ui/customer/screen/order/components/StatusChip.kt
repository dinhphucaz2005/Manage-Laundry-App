package com.example.manage.laundry.ui.customer.screen.order.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.request.Order

@Composable
fun StatusChip(status: Order.Status) {

    Surface(
        color = Color.White,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status.getStringStatusChip(),
            style = MaterialTheme.typography.bodyLarge,
            color = status.getColor(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
