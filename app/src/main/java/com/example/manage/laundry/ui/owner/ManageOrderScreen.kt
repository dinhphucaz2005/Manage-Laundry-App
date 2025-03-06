package com.example.manage.laundry.ui.owner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.request.Order
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel


@Composable
fun ManageOrderScreen(viewModel: ShopOwnerViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(viewModel.uiState.shopOrders) { order ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Đơn #${order.orderId} - ${order.customerName}")
                        Text("${order.totalPrice} VND - ${order.status}")
                    }
                    IconButton(onClick = {
                        viewModel.updateOrder(
                            order.orderId,
                            Order.Status.CONFIRMED,
                            "Giao trước 6h"
                        )
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Cập nhật")
                    }
                }
            }
        }
    }
}
