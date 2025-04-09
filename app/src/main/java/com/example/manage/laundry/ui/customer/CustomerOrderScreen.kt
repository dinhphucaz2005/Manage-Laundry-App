package com.example.manage.laundry.ui.customer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.data.model.response.OrderHistoryResponse
import com.example.manage.laundry.viewmodel.CustomerState
import com.example.manage.laundry.viewmodel.CustomerViewModel
import kotlinx.coroutines.launch

@Composable
fun CustomerOrderScreen(
    viewModel: CustomerViewModel,
    onNavigateToOrderDetail: (Int) -> Unit,
    onNavigateToCreateOrder: () -> Unit
) {
    val orderHistoryState by viewModel.orderHistoryState.collectAsState()
    val trackOrderState by viewModel.trackOrderState.collectAsState()

    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getOrderHistory()
    }

    LaunchedEffect(trackOrderState) {
        if (trackOrderState is CustomerState.TrackOrder.Error) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    (trackOrderState as CustomerState.TrackOrder.Error).message
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Đơn Hàng Của Bạn",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onNavigateToCreateOrder,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tạo Đơn Hàng Mới")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (orderHistoryState) {
            is CustomerState.OrderHistory.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is CustomerState.OrderHistory.Error -> {
                Text(
                    text = "Lỗi: ${(orderHistoryState as CustomerState.OrderHistory.Error).message}",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is CustomerState.OrderHistory.Success -> {
                val orders = (orderHistoryState as CustomerState.OrderHistory.Success).orders
                if (orders.isEmpty()) {
                    Text(
                        text = "Bạn chưa có đơn hàng nào",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        "Lịch Sử Đơn Hàng",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(orders) { order ->
                            OrderItem(
                                order = order,
                                onTrackClick = {
                                    viewModel.trackOrder(order.orderId)
                                    onNavigateToOrderDetail(order.orderId)
                                }
                            )
                        }
                    }
                }
            }

            else -> {} // Idle state, do nothing
        }
    }

    // Display Snackbar for messages
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun OrderItem(order: OrderHistoryResponse, onTrackClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Đơn #${order.orderId}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = getStatusDisplay(order.status),
                    color = getStatusColor(order.status),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Tiệm: ${order.shopName}")
            Text(text = "Tổng tiền: ${order.totalPrice} VND")
            Text(text = "Ngày tạo: ${order.createdAt}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onTrackClick) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Xem chi tiết",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Button(onClick = onTrackClick) {
                    Text("Theo dõi")
                }
            }
        }
    }
}

@Composable
private fun getStatusDisplay(status: String): String {
    return when (status) {
        "PENDING" -> "Chờ xác nhận"
        "CONFIRMED" -> "Đã xác nhận"
        "IN_PROGRESS" -> "Đang xử lý"
        "READY_FOR_DELIVERY" -> "Sẵn sàng giao"
        "COMPLETED" -> "Hoàn thành"
        "CANCELLED" -> "Đã hủy"
        else -> status
    }
}

@Composable
private fun getStatusColor(status: String): Color {
    return when (status) {
        "PENDING" -> Color.Gray
        "CONFIRMED" -> Color.Blue
        "IN_PROGRESS" -> Color(0xFFFFA000) // Amber
        "READY_FOR_DELIVERY" -> Color(0xFF00BCD4) // Cyan
        "COMPLETED" -> Color(0xFF4CAF50) // Green
        "CANCELLED" -> Color.Red
        else -> Color.Gray
    }
}