package com.example.manage.laundry.ui.owner.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel


@Composable
fun ManageOrderScreen(viewModel: ShopOwnerViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        items(viewModel.uiState.shopOrders) { order ->
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp)
//            ) {
//                Row(
//                    modifier = Modifier.padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column(modifier = Modifier.weight(1f)) {
//                        Text("Đơn #${order.orderId} - ${order.customerName}")
//                        Text("${order.totalPrice} VND - ${order.status}")
//                    }
//                    IconButton(onClick = {
//                        viewModel.updateOrder(
//                            order.orderId,
//                            Order.Status.CONFIRMED,
//                            "Giao trước 6h"
//                        )
//                    }) {
//                        Icon(Icons.Default.Edit, contentDescription = "Cập nhật")
//                    }
//                }
//            }
//        }
    }
}
