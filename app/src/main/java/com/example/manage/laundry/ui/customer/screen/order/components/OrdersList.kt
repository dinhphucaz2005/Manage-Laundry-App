package com.example.manage.laundry.ui.customer.screen.order.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.response.OrderResponse


@Composable
fun OrdersList(
    orders: List<OrderResponse>,
    onOrderClick: (OrderResponse) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(orders) { order ->
            OrderCard(order = order, onClick = { onOrderClick(order) })
        }
    }
}
