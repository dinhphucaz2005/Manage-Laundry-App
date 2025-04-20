package com.example.manage.laundry.ui.staff.screen.delivery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.ui.staff.StaffViewModel
import com.example.manage.laundry.ui.staff.screen.delivery.components.DeliveryOrderList

@Composable
fun DeliveryScreen(
    deliveryViewModel: DeliveryViewModel,
    staffViewModel: StaffViewModel
) {
    val uiState by deliveryViewModel.uiState.collectAsState()
    val updateOrderState by staffViewModel.updateOrderState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(updateOrderState) {
        when (updateOrderState) {
            is StaffViewModel.UpdateOrderState.Success -> {
                snackbarHostState.showSnackbar("Cập nhật trạng thái đơn hàng thành công")
                deliveryViewModel.loadCompletedOrders()
            }

            is StaffViewModel.UpdateOrderState.Error -> {
                val errorMessage =
                    (updateOrderState as StaffViewModel.UpdateOrderState.Error).message
                snackbarHostState.showSnackbar(errorMessage)
            }

            StaffViewModel.UpdateOrderState.Idle -> {
                // Do nothing
            }

            StaffViewModel.UpdateOrderState.Loading -> {
                snackbarHostState.showSnackbar("Đang cập nhật trạng thái đơn hàng...")
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        deliveryViewModel.reset()
        staffViewModel.reset()
        deliveryViewModel.loadCompletedOrders()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when {
            uiState.isLoading -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Đang tải danh sách đơn hàng...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            uiState.error != null -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Đã xảy ra lỗi",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.error ?: "Không thể tải danh sách đơn hàng",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = { deliveryViewModel.loadCompletedOrders() }) {
                    Text("Thử lại")
                }
            }

            uiState.orders.isEmpty() -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Không có đơn hàng nào cần giao",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = deliveryViewModel::loadCompletedOrders) {
                    Text("Làm mới")
                }
            }

            else -> DeliveryOrderList(
                orders = uiState.orders,
                onUpdateOrderStatus = staffViewModel::updateOrderStatus
            )
        }
    }
}