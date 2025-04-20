package com.example.manage.laundry.ui.staff.screen.delivery.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.request.Order
import com.example.manage.laundry.data.model.response.OrderResponse

@Composable
fun DeliveryOrderList(
    orders: List<OrderResponse>,
    onUpdateOrderStatus: (Int, Order.Status) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(orders) { order ->
            DeliveryOrderItem(order = order, onUpdateOrderStatus = onUpdateOrderStatus)
        }
    }
}
